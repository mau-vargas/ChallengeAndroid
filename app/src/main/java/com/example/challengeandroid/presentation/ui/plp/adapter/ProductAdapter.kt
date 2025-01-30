package com.example.challengeandroid.presentation.ui.plp.adapter

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
import com.example.challengeandroid.presentation.ui.plp.entity.TypeOfProduct
import com.google.android.material.badge.ExperimentalBadgeUtils
import com.squareup.picasso.Picasso

class ProductAdapter(
    private val product: List<Product>,
    private val itemOnclick: (Int) -> Unit,
    private val addToCar: (Int) -> Unit
) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productName: TextView = view.findViewById(R.id.normalProductName)
        val productPrice: TextView = view.findViewById(R.id.normalProductPrice)
        val productImage: ImageView = view.findViewById(R.id.normalProductImage)

        val contentFeaturedProduct: LinearLayout = view.findViewById(R.id.contentFeaturedProduct)
        val contentItemProduct: LinearLayout = view.findViewById(R.id.contentItemProduct)

        val featuredProductName: TextView = view.findViewById(R.id.featuredProductName)
        val featuredProductPrice: TextView = view.findViewById(R.id.featuredProductPrice)
        val featuredProductImage: ImageView = view.findViewById(R.id.featuredProductImage)


        val contentFeaturedProductCardView: CardView =
            view.findViewById(R.id.contentFeaturedProductCardView)
        val contentItemProductCardView: CardView =
            view.findViewById(R.id.contentItemProductCardView)

        val normalProductAdd: ImageView = view.findViewById(R.id.normalProductAdd)
        val addProduct: ImageView = view.findViewById(R.id.addProduct)

        val lottieViewAddProduct: LottieAnimationView = view.findViewById(R.id.lottieViewAddProduct)
        val featuredLottieViewAddProduct: LottieAnimationView =
            view.findViewById(R.id.featuredLottieViewAddProduct)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    @OptIn(ExperimentalBadgeUtils::class)
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = product[position]
        product.type?.let {
            when (product.type) {
                is TypeOfProduct.Featured -> {
                    holder.contentFeaturedProduct.visibility = View.VISIBLE
                    holder.contentItemProduct.visibility = View.GONE
                    holder.featuredProductName.text = product.title
                    holder.featuredProductPrice.text = "$${product.price}"
                    Picasso.get().load(product.image).into(holder.featuredProductImage)
                    holder.contentFeaturedProductCardView.setOnClickListener {
                        itemOnclick(product.id)
                    }
                    holder.addProduct.setOnClickListener {
                        addToCar(product.id)

                        holder.featuredLottieViewAddProduct.visibility = View.VISIBLE
                        holder.featuredLottieViewAddProduct.playAnimation()

                        holder.normalProductAdd.visibility = View.INVISIBLE


                        holder.featuredLottieViewAddProduct.animate()
                            .alpha(1f)
                            .setDuration(3500)
                            .withEndAction {
                                holder.addProduct.visibility = View.VISIBLE
                                holder.featuredLottieViewAddProduct.visibility = View.INVISIBLE
                            }
                            .start()
                    }
                }

                is TypeOfProduct.Normal -> {
                    holder.contentItemProduct.visibility = View.VISIBLE
                    holder.contentFeaturedProduct.visibility = View.GONE
                    holder.productName.text = product.title
                    holder.productPrice.text = "$${product.price}"
                    Picasso.get().load(product.image).into(holder.productImage)
                    holder.contentItemProductCardView.setOnClickListener {
                        itemOnclick(product.id)
                    }
                    holder.normalProductAdd.setOnClickListener {
                        addToCar(product.id)

                        holder.lottieViewAddProduct.visibility = View.VISIBLE
                        holder.lottieViewAddProduct.playAnimation()

                        holder.normalProductAdd.visibility = View.INVISIBLE


                        holder.lottieViewAddProduct.animate()
                            .alpha(1f)
                            .setDuration(3500)
                            .withEndAction {
                                holder.normalProductAdd.visibility = View.VISIBLE
                                holder.lottieViewAddProduct.visibility = View.INVISIBLE
                            }
                            .start()
                    }
                }

                null -> {}
            }
        }
    }

    override fun getItemCount(): Int {
        return product.size
    }
}