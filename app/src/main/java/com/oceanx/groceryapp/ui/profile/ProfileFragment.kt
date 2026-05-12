package com.oceanx.groceryapp.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.oceanx.groceryapp.R
import com.oceanx.groceryapp.databinding.FragmentProfileBinding
import com.oceanx.groceryapp.utils.ThemeManager

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set dark mode switch state
        binding.switchDarkMode.isChecked = ThemeManager.isDarkMode(requireContext())

        // Dark Mode Toggle
        binding.switchDarkMode.setOnCheckedChangeListener { _, _ ->
            ThemeManager.toggleDarkMode(requireContext())
            requireActivity().recreate()
        }

        // My Orders
        binding.btnMyOrders.setOnClickListener {
            findNavController().navigate(R.id.action_profile_to_orders)
        }

        // Saved Addresses
        binding.btnAddresses.setOnClickListener {
            Toast.makeText(requireContext(), "📍 Addresses coming soon!", Toast.LENGTH_SHORT).show()
        }

        // Help
        binding.btnHelp.setOnClickListener {
            Toast.makeText(requireContext(), "💬 Support: quickgroc@help.com", Toast.LENGTH_SHORT).show()
        }

        // About
        binding.btnAbout.setOnClickListener {
            Toast.makeText(requireContext(), "QuickGroc v1.0 · Made with ❤️", Toast.LENGTH_SHORT).show()
        }

        // Logout
        binding.btnLogout.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Logout") { _, _ ->
                    findNavController().navigate(R.id.action_profile_to_login)
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
