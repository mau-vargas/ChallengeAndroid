package com.example.challengeandroid.presentation.ui.pdp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.challengeandroid.databinding.ProductDetailBottomSheetBinding
import com.example.challengeandroid.domain.entity.Product
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PdpBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: ProductDetailBottomSheetBinding

    private val viewModel: PdpBottomSheetViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProductDetailBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getInt("idValue")

        viewModel.productsLiveData.observe(viewLifecycleOwner, Observer { data ->
            showProductDetail(data)
        })
        id?.let {
            viewModel.getProductById(it)
        }

        binding.normalProductAdd.setOnClickListener {
            id?.let {
                viewModel.addToCart(it)
            }
        }
    }

    private fun showProductDetail(data: Product) {
        binding.textView2.text = data.title
        binding.textView3.text = data.price.toString()
        binding.textView4.text = data.description
        binding.ratingBar.rating = data.rating?.rate?.toFloat() ?: 0f
        Picasso.get().load(data.image).into(binding.imageView3)
    }
}
