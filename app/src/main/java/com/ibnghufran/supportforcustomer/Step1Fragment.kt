package com.ibnghufran.supportforcustomer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class Step1Fragment : Fragment() {

    private lateinit var fullName: EditText
    private lateinit var mobileNumber: EditText
    private lateinit var complaint: EditText
    private lateinit var nextButton: Button
    private var userData: UserInputData? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_step1, container, false)

        fullName = view.findViewById(R.id.fullName)
        mobileNumber = view.findViewById(R.id.mobileNumber)
        complaint = view.findViewById(R.id.complaint)
        nextButton = view.findViewById(R.id.nextButton)

        nextButton.setOnClickListener {
            // Validate input fields
            if (validateFields()) {
                userData = UserInputData(
                    fullName = fullName.text.toString().trim(),
                    mobileNumber = mobileNumber.text.toString().trim(),
                    complaint = complaint.text.toString().trim()
                )
                // Navigate to Step2Fragment and pass userData
                val step2Fragment = Step2Fragment()
                val args = Bundle().apply {
                    putParcelable("userInputData", userData)
                }
                step2Fragment.arguments = args

                (activity as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, step2Fragment)
                    .addToBackStack(null)
                    .commit()
            }
        }

        return view
    }

    private fun validateFields(): Boolean {
        val name = fullName.text.toString().trim()
        val mobile = mobileNumber.text.toString().trim()
        val complaintText = complaint.text.toString().trim()

        return when {
            name.isEmpty() -> {
                Toast.makeText(context, "Please enter your full name", Toast.LENGTH_SHORT).show()
                false
            }
            mobile.isEmpty() -> {
                Toast.makeText(context, "Please enter your mobile number", Toast.LENGTH_SHORT).show()
                false
            }
            complaintText.isEmpty() -> {
                Toast.makeText(context, "Please enter your complaint", Toast.LENGTH_SHORT).show()
                false
            }
            else -> true
        }
    }
}
