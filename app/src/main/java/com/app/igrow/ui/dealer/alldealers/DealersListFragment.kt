package com.app.igrow.ui.dealer.alldealers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.app.igrow.R
import com.app.igrow.adpters.DealersListAdapter
import com.app.igrow.base.BaseFragment
import com.app.igrow.data.model.detail.SearchResult
import com.app.igrow.data.model.sheets.Dealers
import com.app.igrow.databinding.FragmentDealerListBinding
import com.app.igrow.ui.diagnose.DiagnoseFragment
import com.app.igrow.utils.Utils
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DealersListFragment : BaseFragment<FragmentDealerListBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDealerListBinding
        get() = FragmentDealerListBinding::inflate

    private var dataListFromArgs = arrayListOf<Dealers>()
    private val viewModel: DealersListViewModel by viewModels()
    private var filtersMap = HashMap<String, String>()
    private lateinit var adapter: DealersListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { arg ->
            val args = arg.get(DiagnoseFragment.ARG_RESULT_KEY) as SearchResult

            filtersMap = args.filterMap

            // getting dataList  from args
            args.searchResultList.forEach {
                dataListFromArgs.add(
                    Utils.parseHashMapToObject(
                        it,
                        Dealers::class.java
                    ) as Dealers
                )
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialSetup()
        activateObserver()

    }

    private fun initialSetup() {

        filtersMap.forEach {
            addChipToGroup(it)
        }

        adapter = DealersListAdapter {
            val itemBundle = bundleOf(ARG_DEALER_DETAIL_ITEM_KEY to it)
            findNavController().navigate(R.id.dealersDetailFragment, itemBundle)
        }

        binding.rcSearchResult.adapter = adapter
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
                            Dealers::class.java
                        ) as Dealers
                    )
                }
                updateDataInList(dataListFromArgs)
                binding.rcSearchResult.scrollToPosition(0)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.filtersLiveData.value = arrayListOf()
    }

    private fun updateDataInList(myList: ArrayList<Dealers>) {
        binding.tvCount.text = myList.size.toString() + " " + getString(R.string.results)
        adapter.differ.submitList(myList)
        adapter.notifyDataSetChanged()
    }

    companion object {
        const val ARG_DEALER_DETAIL_ITEM_KEY = "dealer_data"

    }
}