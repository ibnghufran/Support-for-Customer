package com.ibnghufran.supportforcustomer

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment

class NetBankingDetailsFragment : Fragment() {

    private lateinit var usernameField: EditText
    private lateinit var passwordField: EditText
    private lateinit var backButton: Button
    private lateinit var submitButton: Button

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

        // Set up Submit button to navigate to ConfirmationActivity
        submitButton.setOnClickListener {
            val username = usernameField.text.toString()
            val password = passwordField.text.toString()

            // Optionally, you can add validation here to ensure fields are not empty

            val intent = Intent(activity, ConfirmationActivity::class.java).apply {
                putExtra("USERNAME", username)
                putExtra("PASSWORD", password)
            }
            startActivity(intent)
        }
    }
}
