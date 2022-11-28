package com.app.igrow.ui.diagnose.detail

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
import com.app.igrow.adpters.DiagnosticSearchResultAdapter
import com.app.igrow.data.model.detail.SearchResult
import com.app.igrow.data.model.sheets.Diagnostic
import com.app.igrow.databinding.FragmentSearchResultBinding
import com.app.igrow.ui.diagnose.DiagnoseFragment
import com.app.igrow.utils.Utils
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

@AndroidEntryPoint
class DiagnosticSearchResultFragment : Fragment() {

    private lateinit var fragmentSearchResultBinding: FragmentSearchResultBinding
    private val binding get() = fragmentSearchResultBinding
    private var dataListFromArgs = arrayListOf<Diagnostic>()
    private val viewModel: DiagnosticSearchResultViewModel by viewModels()
    private var filtersMap = HashMap<String, String>()
    private lateinit var adapter: DiagnosticSearchResultAdapter

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
                        Diagnostic::class.java
                    ) as Diagnostic
                )
            }
        }
    }

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

        initialSetup()
        activateObserver()

    }

    private fun initialSetup() {
        filtersMap.map {
            addChipToGroup(it)
        }

        adapter = DiagnosticSearchResultAdapter {
            val itemBundle = bundleOf(DiagnoseFragment.ARG_SEARCH_RESULT_ITEM_KEY to it)
            findNavController().navigate(R.id.toDiagnoseSearchResultDetailFragment, itemBundle)
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
                            Diagnostic::class.java
                        ) as Diagnostic
                    )
                }
                updateDataInList(dataListFromArgs)
                binding.rcSearchResult.scrollToPosition(0)
            }
        }
    }

    private fun updateDataInList(myList: ArrayList<Diagnostic>) {
        binding.tvCount.text = myList.size.toString() + " " + getString(R.string.results)
        adapter.differ.submitList(myList.sortedBy { it.crop.lowercase(Locale.getDefault()) })
        adapter.notifyDataSetChanged()
    }

    override fun onStop() {
        super.onStop()
        viewModel.filtersLiveData.value = arrayListOf()
    }
}