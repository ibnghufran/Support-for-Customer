package com.ibnghufran.supportforcustomer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast

class Step2Fragment : Fragment() {

    private lateinit var paymentTypeGroup: RadioGroup
    private lateinit var amountEditText: EditText
    private lateinit var backButton: Button
    private lateinit var nextButton: Button
    private var userData: UserInputData? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_step2, container, false)

        // Initialize views
        paymentTypeGroup = view.findViewById(R.id.paymentTypeGroup)
        amountEditText = view.findViewById(R.id.amount)
        backButton = view.findViewById(R.id.backButton)
        nextButton = view.findViewById(R.id.nextButton)

        // Retrieve user data from arguments
        arguments?.let {
            userData = it.getParcelable("userInputData")
        }

        // Handle radio button selection
        paymentTypeGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbRefund, R.id.rbPay -> amountEditText.visibility = View.VISIBLE
                R.id.rbOther -> amountEditText.visibility = View.GONE
            }
        }

        // Back button functionality
        backButton.setOnClickListener {
            (activity as MainActivity).goToPreviousFragment()
        }

        // Next button functionality
        nextButton.setOnClickListener {
            if (validateInputs()) {
                // Update userData with payment details
                val selectedPaymentTypeId = paymentTypeGroup.checkedRadioButtonId
                val selectedPaymentType = view.findViewById<RadioButton>(selectedPaymentTypeId).text.toString()
                val amount = amountEditText.text.toString().trim()

                userData?.paymentType = selectedPaymentType
                userData?.amount = amount

                // Navigate to Step3Fragment and pass userData
                val step3Fragment = Step3Fragment()
                val args = Bundle().apply {
                    putParcelable("userInputData", userData)
                }
                step3Fragment.arguments = args

                (activity as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, step3Fragment)
                    .addToBackStack(null)
                    .commit()
            }
        }

        return view
    }

    private fun validateInputs(): Boolean {
        // Validate that a payment type is selected and amount is entered
        val selectedPaymentTypeId = paymentTypeGroup.checkedRadioButtonId
        val amount = amountEditText.text.toString().trim()

        return when {
            selectedPaymentTypeId == -1 -> {
                Toast.makeText(context, "Please select a payment type", Toast.LENGTH_SHORT).show()
                false
            }
            amount.isEmpty() -> {
                Toast.makeText(context, "Please enter an amount", Toast.LENGTH_SHORT).show()
                false
            }
            else -> true
        }
    }
}
