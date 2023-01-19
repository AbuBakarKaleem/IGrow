package com.app.igrow.ui.dealer

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.igrow.R
import com.app.igrow.adpters.DialogListAdapter
import com.app.igrow.base.BaseFragment
import com.app.igrow.data.model.detail.SearchResult
import com.app.igrow.databinding.DialogeLayoutBinding
import com.app.igrow.databinding.FragmentDealerBinding
import com.app.igrow.ui.admin.LoadingState
import com.app.igrow.ui.admin.UnloadingState
import com.app.igrow.ui.diagnose.DiagnoseFragment
import com.app.igrow.utils.Constants
import com.app.igrow.utils.Utils
import com.app.igrow.utils.gone
import com.app.igrow.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class DealersFragment : BaseFragment<FragmentDealerBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDealerBinding
        get() = FragmentDealerBinding::inflate

    private val viewModel: DealersViewModel by viewModels()

    private var distributorsFiltersHashMap = HashMap<String, String>()
    private var distributorColumnName = ""
    private var dialogList = arrayListOf<String>()
    private var filteredList = arrayListOf<String>()
    private lateinit var dialogListAdapter: DialogListAdapter
    private lateinit var listDialog: Dialog
    private lateinit var dialogLayoutBinding: DialogeLayoutBinding
    private var selectedFilter = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activateListener()
        activateObserver()
    }

    private fun activateListener() {
        try {

            binding.llRegion.setOnClickListener {
                distributorColumnName = Utils.getLocalizeColumnName(Constants.COL_REGION)
                viewModel.getDistributorColumnData(distributorColumnName, Constants.SHEET_DEALERS)
            }
            binding.llCityTown.setOnClickListener {
                distributorColumnName = Utils.getLocalizeColumnName(Constants.COL_CITY_TOWN)
                viewModel.getDistributorColumnData(distributorColumnName, Constants.SHEET_DEALERS)
            }
            binding.llDistributor.setOnClickListener {
                distributorColumnName = Utils.getLocalizeColumnName(Constants.COL_DISTRIBUTORS_NAME)
                viewModel.getDistributorColumnData(
                    distributorColumnName,
                    Constants.SHEET_DISTRIBUTORS
                )
            }

            binding.btnSearch.setOnClickListener {

                if (binding.etSearch.text.toString().isNotEmpty()) {
                    distributorsFiltersHashMap.clear()
                    distributorsFiltersHashMap[Utils.getLocalizeColumnName(Constants.COL_DEALER_NAME)] =
                        binding.etSearch.text.toString().trim()
                }
                viewModel.searchDistributor(distributorsFiltersHashMap)
            }

            binding.btnReset.setOnClickListener {
                resetAllFilters()
            }

            binding.etSearch.doAfterTextChanged { editable ->
                if (editable != null && (editable.isEmpty() || editable.isBlank())) {
                    distributorsFiltersHashMap.remove(Constants.COL_DEALER_NAME)
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun activateObserver() {
        viewModel.uiStateLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is LoadingState -> {
                    binding.pbDistributors.visible()
                }
                is UnloadingState -> {
                    binding.pbDistributors.gone()

                }
            }
        }
        viewModel.getDistributorColumnDataLiveData.observe(viewLifecycleOwner) {
            if (it != null && it.size > 0) {
                it.sort()
                if (distributorColumnName.isNotEmpty()) {
                    addPlaceholderInFilterList(it)
                }
                dialogList = it
                showListDialog(it)
            }
        }

        viewModel.filtersLiveData.observe(viewLifecycleOwner) { response ->
            if (response.isNotEmpty()) {
                val searchResultData =
                    SearchResult(distributorsFiltersHashMap, response)
                val bundle =
                    bundleOf(DiagnoseFragment.ARG_RESULT_KEY to searchResultData)
                findNavController().navigate(R.id.toDealersListFragment, bundle)
            } else {
                if (viewModel.showEmptyListMsg())
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.no_data_found),
                        Toast.LENGTH_LONG
                    ).show()
            }
        }
    }

    private fun showListDialog(dataList: ArrayList<String>) {
        listDialog = Dialog(requireContext())
        listDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        dialogLayoutBinding = DialogeLayoutBinding.inflate(LayoutInflater.from(context))
        listDialog.setContentView(dialogLayoutBinding.root)

        listDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogLayoutBinding.rcListDialog.layoutManager = LinearLayoutManager(requireContext())
        dialogLayoutBinding.rcListDialog.setHasFixedSize(true)

        setDialogListToAdapter(dataList)

        listDialog.show()


        dialogLayoutBinding.etListDialogSearch.doAfterTextChanged { editable ->
            if (!editable.isNullOrEmpty() && editable.isNotBlank()) {
                val myResult = searchList(editable.toString())
                setDialogListToAdapter(myResult)
            } else {
                filteredList.clear()
                setDialogListToAdapter(dialogList)
            }
        }
    }

    private fun resetAllFilters() {
        binding.etSearch.text?.clear()
        if (distributorsFiltersHashMap.isNotEmpty()) {
            binding.tvRegionFilterText.text = getString(R.string.region)
            binding.tvCityTownFilterText.text = getString(R.string.city_town)
            binding.tvDistributorFilterText.text = getString(R.string.distributor)
            distributorsFiltersHashMap.clear()
        }
    }

    private fun searchList(searchValue: String): ArrayList<String> {
        try {
            filteredList.clear()
            if (dialogList.size < 0) {
                return arrayListOf()
            }
            filteredList = dialogList.filter { model ->
                model.lowercase(Locale.getDefault())
                    .contains(searchValue.lowercase(Locale.getDefault()))
            } as ArrayList<String>

            if (filteredList.isEmpty()) {
                filteredList.add(getString(R.string.no_record_found))
                return filteredList
            }
            return filteredList
        } catch (ex: Exception) {
            Log.e(DiagnoseFragment.TAG, ex.stackTraceToString())
        }
        return arrayListOf()
    }

    private fun setDialogListToAdapter(dataList: ArrayList<String>) {
        dialogListAdapter = DialogListAdapter { uiValue, position ->
            val selectedValue = if (filteredList.isNotEmpty() && position < filteredList.size) {
                filteredList[position]
            } else {
                dialogList[position]
            }
            listDialog.dismiss()
            setSelectedValueToView(uiValue)
            populateFiltersObject(selectedValue)
        }
        dialogListAdapter.differ.submitList(dataList)
        dialogLayoutBinding.rcListDialog.adapter = dialogListAdapter
    }

    private fun populateFiltersObject(value: String) {
        if (distributorColumnName.isNotEmpty() &&
            (value == getString(R.string.all_region)).not() &&
            (value == getString(R.string.all_city_towns)).not() &&
            (value == getString(R.string.all_distributors)).not()
        ) {
            distributorsFiltersHashMap[distributorColumnName] = value
        } else {
            distributorsFiltersHashMap.remove(distributorColumnName)
        }
    }

    private fun addPlaceholderInFilterList(dataList: ArrayList<String>){
        when (distributorColumnName) {
            Constants.COL_REGION, Constants.COL_REGION_FR -> {
                dataList.add(0, getString(R.string.all_region))
            }
            Constants.COL_CITY_TOWN, Constants.COL_CITY_TOWN_FR -> {
                dataList.add(0, getString(R.string.all_city_towns))
            }
            Constants.COL_DISTRIBUTORS_NAME, Constants.COL_DISTRIBUTORS_NAME_FR -> {
                dataList.add(0, getString(R.string.all_distributors))
            }
        }
    }

    private fun setSelectedValueToView(value: String) {
        if (distributorColumnName.isNotEmpty()) {
            when (distributorColumnName) {
                Constants.COL_REGION, Constants.COL_REGION_FR -> {
                    binding.tvRegionFilterText.text = ""
                    binding.tvRegionFilterText.text = value
                    return
                }
                Constants.COL_CITY_TOWN, Constants.COL_CITY_TOWN_FR -> {
                    binding.tvCityTownFilterText.text = ""
                    binding.tvCityTownFilterText.text = value
                    return
                }
                Constants.COL_DISTRIBUTORS_NAME, Constants.COL_DISTRIBUTORS_NAME_FR -> {
                    binding.tvDistributorFilterText.text = ""
                    binding.tvDistributorFilterText.text = value
                    return
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.filtersLiveData.value = arrayListOf()
        viewModel.getDistributorColumnDataLiveData.value = arrayListOf()
    }

    override fun onResume() {
        super.onResume()
        distributorsFiltersHashMap.clear()
        filteredList.clear()
        binding.etSearch.text?.clear()
    }

}