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

class Step2Fragment : Fragment() {

    private lateinit var paymentTypeGroup: RadioGroup
    private lateinit var amountEditText: EditText
    private lateinit var backButton: Button
    private lateinit var nextButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_step2, container, false)

        paymentTypeGroup = view.findViewById(R.id.paymentTypeGroup)
        amountEditText = view.findViewById(R.id.amount)
        backButton = view.findViewById(R.id.backButton)
        nextButton = view.findViewById(R.id.nextButton)

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
            (activity as MainActivity).goToNextFragment(2)
        }

        return view
    }
}
