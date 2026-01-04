package com.example.task3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductAdapter(
    private val items: List<Product>,
    private val onItemClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductVH>() {

    class ProductVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductVH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductVH(view)
    }

    override fun onBindViewHolder(holder: ProductVH, position: Int) {
        val product = items[position]
        holder.tvName.text = product.name
        holder.tvPrice.text = "Price: ${product.price}"

        holder.itemView.setOnClickListener {
            onItemClick(product)
        }
    }

    override fun getItemCount(): Int = items.size
}
