package com.oceanx.groceryapp.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.oceanx.groceryapp.R
import com.oceanx.groceryapp.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Validate input & enable button
        binding.etMobile.doAfterTextChanged { text ->
            binding.btnSendOtp.isEnabled = text?.length == 10
            binding.btnSendOtp.alpha = if (text?.length == 10) 1f else 0.6f
        }
        binding.btnSendOtp.alpha = 0.6f
        binding.btnSendOtp.isEnabled = false

        binding.btnSendOtp.setOnClickListener {
            val mobile = binding.etMobile.text.toString().trim()
            if (mobile.length == 10) {
                val action = LoginFragmentDirections.actionLoginToOtp(mobile)
                findNavController().navigate(action)
            } else {
                Toast.makeText(requireContext(), "Please enter a valid 10-digit number", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
