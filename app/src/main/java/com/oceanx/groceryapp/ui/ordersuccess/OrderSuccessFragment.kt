package com.oceanx.groceryapp.ui.ordersuccess

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.oceanx.groceryapp.R
import com.oceanx.groceryapp.databinding.FragmentOrderSuccessBinding
import java.util.Locale

class OrderSuccessFragment : Fragment() {

    private var _binding: FragmentOrderSuccessBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderSuccessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Generate random order ID
        val orderId = "#GRO${(100000..999999).random()}"
        binding.tvOrderId.text = orderId

        // Animate the success icon
        binding.successIconBg.scaleX = 0f
        binding.successIconBg.scaleY = 0f
        binding.successIconBg.animate()
            .scaleX(1f).scaleY(1f)
            .setDuration(500)
            .setStartDelay(100)
            .setInterpolator(android.view.animation.OvershootInterpolator())
            .start()

        // Animate card slide up
        binding.orderInfoCard.translationY = 80f
        binding.orderInfoCard.alpha = 0f
        binding.orderInfoCard.animate()
            .translationY(0f).alpha(1f)
            .setDuration(400).setStartDelay(400).start()

        binding.btnBackToHome.setOnClickListener {
            findNavController().navigate(R.id.action_success_to_home)
        }

        binding.btnTrackOrder.setOnClickListener {
            // In a real app, this would open tracking
            android.widget.Toast.makeText(requireContext(),
                "Tracking: Your order is being prepared! 🚀", android.widget.Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
