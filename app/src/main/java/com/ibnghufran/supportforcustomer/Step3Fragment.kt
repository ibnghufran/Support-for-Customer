package com.ibnghufran.supportforcustomer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ibnghufran.supportforcustomer.databinding.FragmentStep3Binding

class Step3Fragment : Fragment() {

    // Binding for accessing views in fragment_step3.xml
    private var _binding: FragmentStep3Binding? = null
    private val binding get() = _binding!!

    // Variable to store user data passed from previous fragment/activity
    private var userData: UserInputData? = null

    // Inflate the layout and initialize the binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStep3Binding.inflate(inflater, container, false)
        return binding.root
    }

    // Handle view interactions and retrieve data when view is created
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve the user data object from the fragment's arguments
        arguments?.let {
            userData = it.getParcelable("userInputData")
        }

        // Set a click listener on the Next button
        binding.nextButton.setOnClickListener {
            // Check which payment mode is selected by the user
            val selectedPaymentModeId = binding.paymentModeGroup.checkedRadioButtonId

            // Bundle to pass user data to the next fragment
            val args = Bundle().apply {
                putParcelable("userInputData", userData)
            }

            // Navigate to the appropriate fragment based on selected payment mode
            when (selectedPaymentModeId) {
                // If Credit/Debit card is selected, navigate to CardDetailsFragment
                binding.rbCreditDebit.id -> {
                    val cardDetailsFragment = CardDetailsFragment().apply { arguments = args }
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, cardDetailsFragment)
                        .addToBackStack(null)  // Adds transaction to back stack
                        .commit()
                }
                // If UPI is selected, navigate to UPIDetailsFragment
                binding.rbUPI.id -> {
                    val upiDetailsFragment = UPIDetailsFragment().apply { arguments = args }
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, upiDetailsFragment)
                        .addToBackStack(null)
                        .commit()
                }
                // If Net Banking is selected, navigate to NetBankingDetailsFragment
                binding.rbNetBanking.id -> {
                    val netBankingFragment = NetBankingDetailsFragment().apply { arguments = args }
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, netBankingFragment)
                        .addToBackStack(null)
                        .commit()
                }
                // If Other option is selected, navigate to BankDetailsFragment
                binding.rbOther.id -> {
                    val bankDetailsFragment = BankDetailsFragment().apply { arguments = args }
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, bankDetailsFragment)
                        .addToBackStack(null)
                        .commit()
                }
                // Optional: Handle case where no payment option is selected
                else -> {
                    // Display a message or take some action if needed
                }
            }
        }

        // Set a click listener on the Back button to go back to the previous fragment
        binding.backButton.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack() // Navigate to previous screen
        }
    }

    // Clear binding to avoid memory leaks when the view is destroyed
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
