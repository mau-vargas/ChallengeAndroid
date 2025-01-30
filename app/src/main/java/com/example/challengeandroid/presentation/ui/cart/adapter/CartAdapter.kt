package com.example.challengeandroid.presentation.ui.cart.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.OptIn
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.challengeandroid.R
import com.example.challengeandroid.domain.entity.Product
import com.google.android.material.badge.ExperimentalBadgeUtils
import com.squareup.picasso.Picasso

class CartAdapter(
    private var product: List<Product>,
    private val addToCar: (Int) -> Unit,
    private val removeProduct: (Int) -> Unit
) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productName: TextView = view.findViewById(R.id.normalProductName)
        val productPrice: TextView = view.findViewById(R.id.normalProductPrice)
        val productImage: ImageView = view.findViewById(R.id.normalProductImage)
        val contentItemProduct: LinearLayout = view.findViewById(R.id.contentItemProduct)
        val quantity: TextView = view.findViewById(R.id.quantity)
        val normalProductAdd: ImageView = view.findViewById(R.id.normalProductAdd)
        val removeProduct: ImageView = view.findViewById(R.id.removeProduct)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    @OptIn(ExperimentalBadgeUtils::class)
    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val value = product[position]

        holder.contentItemProduct.visibility = View.VISIBLE
        holder.productName.text = value.title
        holder.productPrice.text = "$${value.price}"
        holder.quantity.text = value.quantity.toString()
        Picasso.get().load(value.image).into(holder.productImage)

        holder.normalProductAdd.setOnClickListener {
            addToCar(value.id)
            addProductQuantity(value.id)
        }

        holder.removeProduct.setOnClickListener {
            removeProduct(value.id)
            removeProductQuantity(value.id)
        }
    }

    override fun getItemCount(): Int {
        return product.size
    }

    private fun addProductQuantity(productId: Int) {
        val index = product.indexOfFirst { it.id == productId }
        if (index != -1) {
            val updatedProduct = product[index].copy(quantity = product[index].quantity + 1)
            product = product.toMutableList().apply { set(index, updatedProduct) }

            notifyItemChanged(index)
        }
    }

    private fun removeProductQuantity(productId: Int) {
        val index = product.indexOfFirst { it.id == productId }
        if (index != -1) {
            if (product[index].quantity == 1) {
                removeProductList(productId)
            } else {
                val updatedProduct = product[index].copy(quantity = product[index].quantity - 1)
                product = product.toMutableList().apply { set(index, updatedProduct) }
                notifyItemChanged(index)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun removeProductList(productId: Int) {
        val newProductList = product.filterNot { it.id == productId }
        val index = product.indexOfFirst { it.id == productId }

        if (index != -1) {
            product = newProductList
            notifyItemRemoved(index)
        }
    }


}