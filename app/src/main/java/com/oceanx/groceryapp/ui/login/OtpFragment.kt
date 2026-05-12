package com.oceanx.groceryapp.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.oceanx.groceryapp.databinding.FragmentOtpBinding

class OtpFragment : Fragment() {

    private var _binding: FragmentOtpBinding? = null
    private val binding get() = _binding!!
    private val args: OtpFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOtpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Show masked mobile number
        val mobile = args.mobile
        binding.tvMobileNumber.text = "+91 ${mobile.take(3)}XXXXX${mobile.takeLast(2)}"

        // Setup OTP box auto-jump
        setupOtpBoxes()

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.tvResend.setOnClickListener {
            clearOtpBoxes()
            Toast.makeText(requireContext(), "OTP resent! Use 1234", Toast.LENGTH_SHORT).show()
        }

        binding.btnVerify.setOnClickListener {
            val otp = getEnteredOtp()
            if (otp.length < 4) {
                Toast.makeText(requireContext(), "Please enter the complete 4-digit OTP", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (otp == "1234") {
                findNavController().navigate(OtpFragmentDirections.actionOtpToHome())
            } else {
                Toast.makeText(requireContext(), "Invalid OTP. Use 1234", Toast.LENGTH_SHORT).show()
                shakeOtpBoxes()
                clearOtpBoxes()
            }
        }

        // Auto focus first box
        binding.etOtp1.requestFocus()
        val imm = requireContext().getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.etOtp1, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun setupOtpBoxes() {
        val boxes = listOf(binding.etOtp1, binding.etOtp2, binding.etOtp3, binding.etOtp4)
        boxes.forEachIndexed { index, editText ->
            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    if (s?.length == 1) {
                        if (index < boxes.size - 1) boxes[index + 1].requestFocus()
                    }
                }
            })

            editText.setOnKeyListener { _, keyCode, _ ->
                if (keyCode == android.view.KeyEvent.KEYCODE_DEL && editText.text.isEmpty()) {
                    if (index > 0) boxes[index - 1].requestFocus()
                }
                false
            }
        }
    }

    private fun getEnteredOtp(): String {
        return "${binding.etOtp1.text}${binding.etOtp2.text}${binding.etOtp3.text}${binding.etOtp4.text}"
    }

    private fun clearOtpBoxes() {
        listOf(binding.etOtp1, binding.etOtp2, binding.etOtp3, binding.etOtp4).forEach { it.setText("") }
        binding.etOtp1.requestFocus()
    }

    private fun shakeOtpBoxes() {
        val shake = android.view.animation.AnimationUtils.loadAnimation(requireContext(), android.R.anim.shake)
        binding.otpContainer.startAnimation(shake)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
