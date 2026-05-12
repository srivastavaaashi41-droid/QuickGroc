package com.oceanx.groceryapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.oceanx.groceryapp.R
import com.oceanx.groceryapp.data.model.Category
import com.oceanx.groceryapp.data.model.Product
import com.oceanx.groceryapp.databinding.FragmentHomeBinding
import com.oceanx.groceryapp.ui.cart.CartViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val cartViewModel: CartViewModel by activityViewModels()
    private lateinit var productAdapter: ProductAdapter
    private lateinit var categoryAdapter: CategoryAdapter

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

    private val categories = listOf(
        Category(0, "All",     "🛒", R.color.cat_1),
        Category(1, "Veggies", "🥦", R.color.cat_2),
        Category(2, "Fruits",  "🍎", R.color.cat_3),
        Category(3, "Dairy",   "🥛", R.color.cat_4),
        Category(4, "Bakery",  "🍞", R.color.cat_5),
        Category(5, "Staples", "🌾", R.color.cat_6),
    )

    private var currentCategory = "All"
    private var searchQuery = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCategoryRecyclerView()
        setupProductRecyclerView()
        observeCart()
        setupSearch()

        // Cart button
        binding.cartButton.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_cart)
        }

        // Dark mode toggle button
        binding.btnDarkMode.setOnClickListener {
            com.oceanx.groceryapp.utils.ThemeManager.toggleDarkMode(requireContext())
            requireActivity().recreate()
        }

        // "See all →" buttons → navigate to SeeAll screen
        binding.tvSeeAllCategories.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_see_all)
        }
        binding.tvSeeAllProducts.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_see_all)
        }

        filterProducts()
    }

    private fun setupCategoryRecyclerView() {
        categoryAdapter = CategoryAdapter(categories) { category ->
            currentCategory = category.name
            filterProducts()
        }
        binding.rvCategories.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
        }
    }

    private fun setupProductRecyclerView() {
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
        binding.rvProducts.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = productAdapter
        }
    }

    private fun observeCart() {
        cartViewModel.cartItems.observe(viewLifecycleOwner) { cartItems ->
            allProducts.forEach { product ->
                val qty = cartItems.find { it.productId == product.id }?.quantity ?: 0
                productAdapter.updateQuantity(product.id, qty)
            }
        }
        cartViewModel.totalItemCount.observe(viewLifecycleOwner) { count ->
            val total = count ?: 0
            binding.tvCartBadge.visibility = if (total > 0) View.VISIBLE else View.GONE
            binding.tvCartBadge.text = if (total > 9) "9+" else total.toString()
        }
    }

    private fun setupSearch() {
        binding.etSearch.doAfterTextChanged { text ->
            searchQuery = text?.toString() ?: ""
            filterProducts()
        }
    }

    private fun filterProducts() {
        val filtered = allProducts.filter { product ->
            val matchesCategory = currentCategory == "All" || product.category == currentCategory
            val matchesSearch = searchQuery.isEmpty() ||
                    product.name.contains(searchQuery, ignoreCase = true) ||
                    product.category.contains(searchQuery, ignoreCase = true)
            matchesCategory && matchesSearch
        }
        productAdapter.submitList(filtered)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
