package com.oceanx.groceryapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.oceanx.groceryapp.data.model.Product
import com.oceanx.groceryapp.databinding.FragmentSeeAllBinding
import com.oceanx.groceryapp.ui.cart.CartViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SeeAllFragment : Fragment() {

    private var _binding: FragmentSeeAllBinding? = null
    private val binding get() = _binding!!
    private val cartViewModel: CartViewModel by activityViewModels()
    private lateinit var productAdapter: ProductAdapter

    private val allProducts = listOf(
        Product(1,  "Fresh Tomatoes",  "500g",  35,  emoji = "🍅", category = "Veggies", discountPercent = 10),
        Product(2,  "Baby Spinach",    "250g",  29,  emoji = "🥬", category = "Veggies"),
        Product(3,  "Onions",          "1kg",   49,  emoji = "🧅", category = "Veggies"),
        Product(4,  "Potatoes",        "2kg",   59,  emoji = "🥔", category = "Veggies"),
        Product(5,  "Carrots",         "500g",  39,  emoji = "🥕", category = "Veggies"),
        Product(6,  "Broccoli",        "400g",  55,  emoji = "🥦", category = "Veggies"),
        Product(7,  "Bananas",         "6 pcs", 45,  emoji = "🍌", category = "Fruits"),
        Product(8,  "Apples",          "4 pcs", 89,  emoji = "🍎", category = "Fruits", discountPercent = 15),
        Product(9,  "Oranges",         "4 pcs", 69,  emoji = "🍊", category = "Fruits"),
        Product(10, "Grapes",          "500g",  79,  emoji = "🍇", category = "Fruits"),
        Product(11, "Whole Milk",      "1L",    68,  emoji = "🥛", category = "Dairy"),
        Product(12, "Curd",            "400g",  42,  emoji = "🫙", category = "Dairy"),
        Product(13, "Paneer",          "200g",  95,  emoji = "🧀", category = "Dairy"),
        Product(14, "Eggs",            "12 pcs",79,  emoji = "🥚", category = "Dairy"),
        Product(15, "Bread",           "400g",  45,  emoji = "🍞", category = "Bakery"),
        Product(16, "Butter",          "100g",  55,  emoji = "🧈", category = "Dairy"),
        Product(17, "Rice",            "1kg",   89,  emoji = "🍚", category = "Staples"),
        Product(18, "Atta (Wheat)",    "5kg",   245, emoji = "🌾", category = "Staples"),
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSeeAllBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }
        binding.tvProductCount.text = "${allProducts.size} items"

        productAdapter = ProductAdapter(
            onAddClick = { cartViewModel.addToCart(it) },
            onIncreaseClick = { cartViewModel.addToCart(it) },
            onDecreaseClick = { product ->
                CoroutineScope(Dispatchers.Main).launch {
                    val item = cartViewModel.getCartItemForProduct(product.id)
                    item?.let { cartViewModel.decreaseQty(it) }
                }
            }
        )

        binding.rvAllProducts.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvAllProducts.adapter = productAdapter
        productAdapter.submitList(allProducts)

        // Observe cart
        cartViewModel.cartItems.observe(viewLifecycleOwner) { cartItems ->
            allProducts.forEach { product ->
                val qty = cartItems.find { it.productId == product.id }?.quantity ?: 0
                productAdapter.updateQuantity(product.id, qty)
            }
        }

        // Search
        binding.etSearch.doAfterTextChanged { text ->
            val query = text?.toString() ?: ""
            val filtered = if (query.isEmpty()) allProducts
            else allProducts.filter {
                it.name.contains(query, ignoreCase = true) ||
                it.category.contains(query, ignoreCase = true)
            }
            productAdapter.submitList(filtered)
            binding.tvProductCount.text = "${filtered.size} items"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
