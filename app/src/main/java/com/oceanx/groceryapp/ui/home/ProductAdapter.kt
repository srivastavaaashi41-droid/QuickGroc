package com.oceanx.groceryapp.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.oceanx.groceryapp.data.model.Product
import com.oceanx.groceryapp.databinding.ItemProductBinding

class ProductAdapter(
    private val onAddClick: (Product) -> Unit,
    private val onIncreaseClick: (Product) -> Unit,
    private val onDecreaseClick: (Product) -> Unit
) : ListAdapter<Product, ProductAdapter.ProductViewHolder>(ProductDiffCallback()) {

    // Track quantities for each product
    private val quantityMap = mutableMapOf<Int, Int>()

    fun updateQuantity(productId: Int, qty: Int) {
        quantityMap[productId] = qty
        notifyItemChanged(currentList.indexOfFirst { it.id == productId })
    }

    inner class ProductViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.tvProductName.text = product.name
            binding.tvProductWeight.text = product.weight
            binding.tvPrice.text = "₹${product.price}"
            binding.ivProduct.text = product.emoji  // Using emoji as image placeholder

            val qty = quantityMap[product.id] ?: 0

            if (qty == 0) {
                binding.btnAdd.visibility = View.VISIBLE
                binding.qtyControls.visibility = View.GONE
            } else {
                binding.btnAdd.visibility = View.GONE
                binding.qtyControls.visibility = View.VISIBLE
                binding.tvQty.text = qty.toString()
            }

            if (product.discountPercent > 0) {
                binding.tvDiscount.visibility = View.VISIBLE
                binding.tvDiscount.text = "${product.discountPercent}% OFF"
            }

            binding.btnAdd.setOnClickListener {
                onAddClick(product)
            }
            binding.btnPlus.setOnClickListener {
                onIncreaseClick(product)
            }
            binding.btnMinus.setOnClickListener {
                onDecreaseClick(product)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Product, newItem: Product) = oldItem == newItem
    }
}
