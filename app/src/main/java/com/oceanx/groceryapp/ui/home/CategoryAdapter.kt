package com.oceanx.groceryapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oceanx.groceryapp.data.model.Category
import com.oceanx.groceryapp.databinding.ItemCategoryBinding

class CategoryAdapter(
    private val categories: List<Category>,
    private val onClick: (Category) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var selectedIndex = 0

    inner class CategoryViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(category: Category, isSelected: Boolean) {
            binding.tvCategoryEmoji.text = category.emoji
            binding.tvCategoryName.text = category.name

            if (isSelected) {
                binding.categoryIconBg.setBackgroundResource(com.oceanx.groceryapp.R.drawable.bg_add_button)
                binding.tvCategoryName.setTextColor(
                    binding.root.context.getColor(com.oceanx.groceryapp.R.color.primary)
                )
            } else {
                binding.categoryIconBg.setBackgroundResource(com.oceanx.groceryapp.R.drawable.bg_success_circle)
                binding.tvCategoryName.setTextColor(
                    binding.root.context.getColor(com.oceanx.groceryapp.R.color.text_primary)
                )
            }

            binding.root.setOnClickListener {
                val prevIndex = selectedIndex
                selectedIndex = adapterPosition
                notifyItemChanged(prevIndex)
                notifyItemChanged(selectedIndex)
                onClick(category)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position], position == selectedIndex)
    }

    override fun getItemCount() = categories.size
}
