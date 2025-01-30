package com.example.challengeandroid.presentation.ui.plp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.challengeandroid.R
import com.example.challengeandroid.databinding.FragmentHomeBinding
import com.example.challengeandroid.domain.entity.Product
import com.example.challengeandroid.presentation.ui.plp.adapter.ProductAdapter
import com.example.challengeandroid.presentation.ui.plp.entity.TypeOfProduct
import com.example.challengeandroid.util.UIState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlpFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private val productViewModel: PlpViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productViewModel.productsLiveData.observe(requireActivity(), Observer { state ->

            when (state) {
                is UIState.Loading -> {
                    Log.e("", "")
                }

                is UIState.Success -> {
                    showProducts(state.data)
                }

                is UIState.Error -> {
                }

            }
        })




    }

    private fun showProducts(data: List<Product>) {
        val recyclerView: RecyclerView = binding.recyclerViewProduct
        val gridLayoutManager = GridLayoutManager(this.context, 2)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val product = data[position]
                return when (product.type) {
                    is TypeOfProduct.Featured -> 2
                    is TypeOfProduct.Normal -> 1
                    null -> 1
                }
            }
        }

        recyclerView.layoutManager = gridLayoutManager

        val adapter = ProductAdapter(data, ::itemOnclick, ::addToCar)
        recyclerView.adapter = adapter
    }

    fun itemOnclick(id: Int) {
        val bundle = Bundle().apply {
            putInt("idValue", id)
        }

        findNavController().navigate(R.id.action_nav_home_to_nav_pdpBottomSheet, bundle)
    }

    fun addToCar(id: Int) {
        productViewModel.addToCart(id)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}