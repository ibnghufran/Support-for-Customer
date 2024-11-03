package com.ibnghufran.supportforcustomer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ibnghufran.supportforcustomer.databinding.FragmentStep3Binding

class Step3Fragment : Fragment() {

    private var _binding: FragmentStep3Binding? = null
    private val binding get() = _binding!!
    private var userData: UserInputData? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStep3Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve user data from arguments
        arguments?.let {
            userData = it.getParcelable("userInputData")
        }

        binding.nextButton.setOnClickListener {
            val selectedPaymentModeId = binding.paymentModeGroup.checkedRadioButtonId
            when (selectedPaymentModeId) {
                binding.rbCreditDebit.id -> {
                    // Navigate to CardDetailsFragment if Credit/Debit Card is selected
                    val cardDetailsFragment = CardDetailsFragment()
                    val args = Bundle().apply {
                        putParcelable("userInputData", userData)
                    }
                    cardDetailsFragment.arguments = args

                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, cardDetailsFragment)
                        .addToBackStack(null) // Allows back navigation
                        .commit()
                }
                binding.rbUPI.id -> {
                    // Navigate to UPIDetailsFragment if UPI is selected
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, UPIDetailsFragment())
                        .addToBackStack(null)
                        .commit()
                }
                binding.rbNetBanking.id -> {
                    // Navigate to NetBankingDetailsFragment if Net Banking is selected
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, NetBankingDetailsFragment())
                        .addToBackStack(null)
                        .commit()
                }
                binding.rbOther.id -> {
                    // Navigate to BankDetailsFragment if Other is selected
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, BankDetailsFragment())
                        .addToBackStack(null)
                        .commit()
                }
                else -> {
                    // Handle cases where no valid option is selected (optional)
                }
            }
        }

        binding.backButton.setOnClickListener {
            // Navigate back to the previous fragment
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
