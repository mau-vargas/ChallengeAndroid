package com.example.challengeandroid.presentation.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.challengeandroid.databinding.FragmentCartBinding
import com.example.challengeandroid.domain.entity.Product
import com.example.challengeandroid.presentation.ui.cart.adapter.CartAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null

    private val binding get() = _binding!!

    private val cartViewModel: CartViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cartViewModel.cartLiveData.observe(viewLifecycleOwner, Observer { data ->
            showProducts(data)
        })
        cartViewModel.getCartInformation()

        val totalAmount: TextView = binding.totalAmount
        cartViewModel.totalAmount.observe(viewLifecycleOwner, Observer { data ->
            totalAmount.text = data
        })
    }


    private fun showProducts(data: List<Product>) {
        val recyclerView: RecyclerView = binding.recyclerViewCart
        recyclerView.layoutManager = GridLayoutManager(this.context, 1)

        val adapter = CartAdapter(data, ::addToCar, ::removeProduct)
        recyclerView.adapter = adapter

        showTotalAmount(data)
    }

    fun showTotalAmount(data: List<Product>) {
        cartViewModel.getTotalAmount(data)
    }

    fun addToCar(id: Int) {
        cartViewModel.addToCart(id)
    }

    fun removeProduct(id: Int) {
        cartViewModel.removeProduct(id)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
