package com.oceanx.groceryapp.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.oceanx.groceryapp.R
import com.oceanx.groceryapp.databinding.FragmentCartBinding

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private val cartViewModel: CartViewModel by activityViewModels()
    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeCart()

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }
        binding.btnShopNow.setOnClickListener { findNavController().popBackStack() }
        binding.btnCheckout.setOnClickListener {
            findNavController().navigate(R.id.action_cart_to_checkout)
        }
    }

    private fun setupRecyclerView() {
        cartAdapter = CartAdapter(
            onIncrease = { cartViewModel.increaseQty(it) },
            onDecrease = { cartViewModel.decreaseQty(it) },
            onDelete = { cartViewModel.deleteItem(it) }
        )
        binding.rvCartItems.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cartAdapter
        }
    }

    private fun observeCart() {
        cartViewModel.cartItems.observe(viewLifecycleOwner) { items ->
            cartAdapter.submitList(items)

            val isEmpty = items.isEmpty()
            binding.emptyCartView.visibility = if (isEmpty) View.VISIBLE else View.GONE
            binding.rvCartItems.visibility = if (isEmpty) View.GONE else View.VISIBLE
            binding.checkoutCard.visibility = if (isEmpty) View.GONE else View.VISIBLE

            binding.tvItemCount.text = "${items.sumOf { it.quantity }} items"
        }

        cartViewModel.totalPrice.observe(viewLifecycleOwner) { total ->
            val itemTotal = total ?: 0
            val grandTotal = itemTotal + 20 // delivery
            binding.tvItemTotal.text = "₹$itemTotal"
            binding.tvTotal.text = "₹$grandTotal"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
