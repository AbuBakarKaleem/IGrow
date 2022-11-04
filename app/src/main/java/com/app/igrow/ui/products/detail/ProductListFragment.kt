package com.app.igrow.ui.products.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.app.igrow.R
import com.app.igrow.adpters.ProductsSearchResultAdapter
import com.app.igrow.data.model.detail.SearchResult
import com.app.igrow.data.model.sheets.Products
import com.app.igrow.databinding.FragmentProductListBinding
import com.app.igrow.ui.diagnose.DiagnoseFragment
import com.app.igrow.utils.Utils
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListFragment : Fragment() {

    private lateinit var fragmentProductListBinding: FragmentProductListBinding
    private val binding get() = fragmentProductListBinding

    private var dataListFromArgs = arrayListOf<Products>()
    private lateinit var adapter: ProductsSearchResultAdapter
    private var filtersMap= HashMap<String,String>()
    private val viewModel:ProductsListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragmentProductListBinding =
            FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let { arg ->
            val args = arg.get(DiagnoseFragment.ARG_RESULT_KEY) as SearchResult
            filtersMap = args.filterMap

            args.searchResultList.forEach {
                dataListFromArgs.add(
                    Utils.parseHashMapToObject(
                        it,
                        Products::class.java
                    ) as Products
                )
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activateObserver()
        initialSetup()
    }

    private fun initialSetup() {

        filtersMap.forEach{
            addChipToGroup(it)
        }

        adapter = ProductsSearchResultAdapter {
            val itemBundle = bundleOf(DiagnoseFragment.ARG_SEARCH_RESULT_ITEM_KEY to it)
            findNavController().navigate(R.id.toProductSearchResultDetailFragment, itemBundle)
        }

        binding.rcProductSearchResult.adapter = adapter
        updateDataInList(dataListFromArgs)
    }

    private fun addChipToGroup(filter: Map.Entry<String, String>) {
        val chip = Chip(context)
        chip.text = filter.value
        chip.chipIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_cancel)
        chip.isChipIconVisible = false
        chip.isCloseIconVisible = true
        chip.tag = filter.key

        // necessary to get single selection working
        chip.isClickable = true
        chip.isCheckable = false
        binding.chipGroup.addView(chip as View)
        chip.setOnCloseIconClickListener {
            binding.chipGroup.removeView(chip as View)
            filtersMap.remove(it.tag)
            viewModel.searchDiagnostic(filtersMap)
        }
    }

    private fun activateObserver() {

        viewModel.filtersLiveData.observe(viewLifecycleOwner) { response ->
            if (response.isNotEmpty()) {

                dataListFromArgs.clear()
                response.forEach {
                    dataListFromArgs.add(
                        Utils.parseHashMapToObject(
                            it,
                            Products::class.java
                        ) as Products
                    )
                }
                updateDataInList(dataListFromArgs)
                binding.rcProductSearchResult.scrollToPosition(0)
            }
        }
    }


    private fun updateDataInList(myList: ArrayList<Products>) {
        binding.tvCount.text = myList.size.toString() +" "+ getString(R.string.results)
        adapter.differ.submitList(myList)
        adapter.notifyDataSetChanged()
    }

}