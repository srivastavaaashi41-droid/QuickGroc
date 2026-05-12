package com.oceanx.groceryapp.ui.orders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.oceanx.groceryapp.R
import com.oceanx.groceryapp.data.model.Order
import com.oceanx.groceryapp.data.model.OrderStatus
import com.oceanx.groceryapp.databinding.ItemOrderBinding

class OrderAdapter(
    private val orders: List<Order>,
    private val onTrack: (Order) -> Unit,
    private val onReorder: (Order) -> Unit
) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    inner class OrderViewHolder(private val binding: ItemOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(order: Order) {
            binding.tvOrderId.text = order.orderId
            binding.tvOrderDate.text = "${order.date} · ${order.itemCount} items"
            binding.tvItemsEmoji.text = order.itemsEmoji
            binding.tvOrderTotal.text = "₹${order.total}"

            when (order.status) {
                OrderStatus.DELIVERED -> {
                    binding.tvOrderStatus.text = "✓ Delivered"
                    binding.tvOrderStatus.setBackgroundResource(R.drawable.bg_status_delivered)
                    binding.tvOrderStatus.setTextColor(
                        ContextCompat.getColor(binding.root.context, R.color.primary)
                    )
                    binding.trackingContainer.visibility = View.GONE
                    binding.btnTrack.visibility = View.GONE
                }
                OrderStatus.OUT_FOR_DELIVERY -> {
                    binding.tvOrderStatus.text = "🛵 On the way"
                    binding.tvOrderStatus.setBackgroundResource(R.drawable.bg_status_active)
                    binding.tvOrderStatus.setTextColor(
                        ContextCompat.getColor(binding.root.context, R.color.accent)
                    )
                    binding.trackingContainer.visibility = View.VISIBLE
                    binding.btnTrack.visibility = View.VISIBLE
                }
                OrderStatus.PREPARING -> {
                    binding.tvOrderStatus.text = "👨‍🍳 Preparing"
                    binding.tvOrderStatus.setBackgroundResource(R.drawable.bg_status_active)
                    binding.tvOrderStatus.setTextColor(
                        ContextCompat.getColor(binding.root.context, R.color.accent)
                    )
                    binding.trackingContainer.visibility = View.VISIBLE
                    binding.btnTrack.visibility = View.VISIBLE
                }
                OrderStatus.PLACED -> {
                    binding.tvOrderStatus.text = "📋 Placed"
                    binding.tvOrderStatus.setBackgroundResource(R.drawable.bg_status_active)
                    binding.tvOrderStatus.setTextColor(
                        ContextCompat.getColor(binding.root.context, R.color.accent)
                    )
                    binding.trackingContainer.visibility = View.VISIBLE
                    binding.btnTrack.visibility = View.VISIBLE
                }
            }

            binding.btnTrack.setOnClickListener { onTrack(order) }
            binding.btnReorder.setOnClickListener { onReorder(order) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) =
        holder.bind(orders[position])

    override fun getItemCount() = orders.size
}
