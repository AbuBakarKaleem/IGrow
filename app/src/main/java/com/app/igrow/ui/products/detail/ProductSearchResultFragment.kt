package com.app.igrow.ui.products.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.igrow.R
import com.app.igrow.adpters.ProductsSearchResultAdapter
import com.app.igrow.data.model.detail.SearchResult
import com.app.igrow.data.model.sheets.Products
import com.app.igrow.databinding.FragmentSearchResultBinding
import com.app.igrow.ui.diagnose.DiagnoseFragment
import com.app.igrow.utils.Utils
import com.google.android.material.chip.Chip

class ProductSearchResultFragment : Fragment() {

    private lateinit var fragmentSearchResultBinding: FragmentSearchResultBinding
    private val binding get() = fragmentSearchResultBinding

    private var searchResultList = arrayListOf<Products>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragmentSearchResultBinding =
            FragmentSearchResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let { arg ->
            val searchArgs = arg.get(DiagnoseFragment.ARG_RESULT_KEY) as SearchResult
            searchArgs.filterMap.forEach {
                addChipToGroup(it.value)
            }
            searchArgs.searchResultList.forEach {
                searchResultList.add(
                    Utils.parseHashMapToObject(
                        it,
                        Products::class.java
                    ) as Products
                )
            }
            val adapter = ProductsSearchResultAdapter {
                val itemBundle = bundleOf(DiagnoseFragment.ARG_SEARCH_RESULT_ITEM_KEY to it)
                findNavController().navigate(R.id.toProductSearchResultDetailFragment, itemBundle)
            }

            adapter.differ.submitList(searchResultList)
            binding.rcSearchResult.run {
                this.layoutManager = LinearLayoutManager(requireContext())
                this.setHasFixedSize(true)
                this.adapter = adapter
            }

        }

    }

    private fun addChipToGroup(value: String) {
        val chip = Chip(context)
        chip.text = value
        chip.chipIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_cancel)
        chip.isChipIconVisible = false
        chip.isCloseIconVisible = true

        // necessary to get single selection working
        chip.isClickable = true
        chip.isCheckable = false
        binding.chipGroup.addView(chip as View)
        chip.setOnCloseIconClickListener {
            binding.chipGroup.removeView(chip as View)
        }
    }

}