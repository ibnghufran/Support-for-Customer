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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentStep3Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nextButton.setOnClickListener {
            val selectedPaymentModeId = binding.paymentModeGroup.checkedRadioButtonId
            when (selectedPaymentModeId) {
                binding.rbCreditDebit.id -> {
                    // Navigate to CardDetailsFragment if Credit/Debit Card is selected
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, CardDetailsFragment())
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
