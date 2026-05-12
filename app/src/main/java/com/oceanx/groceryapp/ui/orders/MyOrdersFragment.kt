package com.oceanx.groceryapp.ui.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.oceanx.groceryapp.data.model.Order
import com.oceanx.groceryapp.data.model.OrderStatus
import com.oceanx.groceryapp.databinding.FragmentMyOrdersBinding

class MyOrdersFragment : Fragment() {

    private var _binding: FragmentMyOrdersBinding? = null
    private val binding get() = _binding!!

    // Demo orders list
    private val demoOrders = listOf(
        Order(
            orderId = "#GRO847261",
            date = "11 May 2026",
            itemCount = 4,
            itemsEmoji = "🍅  🥬  🥛  🍞",
            total = 184,
            status = OrderStatus.OUT_FOR_DELIVERY
        ),
        Order(
            orderId = "#GRO651032",
            date = "9 May 2026",
            itemCount = 3,
            itemsEmoji = "🍎  🧅  🥚",
            total = 157,
            status = OrderStatus.DELIVERED
        ),
        Order(
            orderId = "#GRO512874",
            date = "5 May 2026",
            itemCount = 5,
            itemsEmoji = "🥦  🥕  🧀  🍌  🥛",
            total = 312,
            status = OrderStatus.DELIVERED
        ),
        Order(
            orderId = "#GRO398201",
            date = "1 May 2026",
            itemCount = 2,
            itemsEmoji = "🌾  🍚",
            total = 334,
            status = OrderStatus.DELIVERED
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }

        val adapter = OrderAdapter(
            orders = demoOrders,
            onTrack = { order ->
                Toast.makeText(
                    requireContext(),
                    "🛵 ${order.orderId} is on the way! Arriving in 5–10 mins",
                    Toast.LENGTH_SHORT
                ).show()
            },
            onReorder = { order ->
                Toast.makeText(
                    requireContext(),
                    "✅ ${order.orderId} added to cart! Go to cart to checkout.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        )

        binding.rvOrders.layoutManager = LinearLayoutManager(requireContext())
        binding.rvOrders.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
