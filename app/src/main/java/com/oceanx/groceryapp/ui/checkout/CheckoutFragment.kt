package com.oceanx.groceryapp.ui.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.oceanx.groceryapp.R
import com.oceanx.groceryapp.databinding.FragmentCheckoutBinding
import com.oceanx.groceryapp.ui.cart.CartViewModel

class CheckoutFragment : Fragment() {

    private var _binding: FragmentCheckoutBinding? = null
    private val binding get() = _binding!!
    private val cartViewModel: CartViewModel by activityViewModels()
    private var selectedPayment = "COD"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCheckoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeCart()
        setupPaymentSelection()
        binding.btnBack.setOnClickListener { findNavController().popBackStack() }
        binding.btnPlaceOrder.setOnClickListener {
            if (validateFields()) {
                cartViewModel.clearCart()
                findNavController().navigate(R.id.action_checkout_to_success)
            }
        }
    }

    private fun observeCart() {
        cartViewModel.totalPrice.observe(viewLifecycleOwner) { total ->
            val itemTotal = total ?: 0
            binding.tvItemTotal.text = "₹$itemTotal"
            binding.tvGrandTotal.text = "₹${itemTotal + 23}"
        }
    }

    private fun setupPaymentSelection() {
        // COD selected by default
        selectCOD()

        binding.layoutCOD.setOnClickListener {
            selectedPayment = "COD"
            selectCOD()
        }
        binding.layoutOnline.setOnClickListener {
            selectedPayment = "Online"
            selectOnline()
        }
    }

    private fun selectCOD() {
        binding.layoutCOD.setBackgroundResource(R.drawable.bg_payment_cod_selected)
        binding.layoutOnline.setBackgroundResource(R.drawable.bg_payment_normal)
        binding.ivCodSelected.setImageResource(R.drawable.ic_radio_on)
        binding.ivOnlineSelected.setImageResource(R.drawable.ic_radio_off)
    }

    private fun selectOnline() {
        binding.layoutCOD.setBackgroundResource(R.drawable.bg_payment_normal)
        binding.layoutOnline.setBackgroundResource(R.drawable.bg_payment_cod_selected)
        binding.ivCodSelected.setImageResource(R.drawable.ic_radio_off)
        binding.ivOnlineSelected.setImageResource(R.drawable.ic_radio_on)
    }

    private fun validateFields(): Boolean {
        val address = binding.etAddress.text.toString().trim()
        val city = binding.etCity.text.toString().trim()
        val pincode = binding.etPincode.text.toString().trim()

        if (address.isEmpty()) {
            binding.etAddress.error = "Please enter your address"
            binding.etAddress.requestFocus()
            return false
        }
        if (city.isEmpty()) {
            binding.etCity.error = "Please enter city"
            binding.etCity.requestFocus()
            return false
        }
        if (pincode.length != 6) {
            binding.etPincode.error = "Please enter valid 6-digit PIN code"
            binding.etPincode.requestFocus()
            return false
        }
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
