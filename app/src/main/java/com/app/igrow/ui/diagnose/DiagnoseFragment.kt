package com.app.igrow.ui.diagnose

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
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.igrow.R
import com.app.igrow.adpters.DialogListAdapter
import com.app.igrow.base.BaseFragment
import com.app.igrow.data.model.detail.SearchResult
import com.app.igrow.databinding.DialogeLayoutBinding
import com.app.igrow.databinding.FragmentDiagnoseBinding
import com.app.igrow.ui.admin.LoadingState
import com.app.igrow.ui.admin.UnloadingState
import com.app.igrow.ui.products.ProductsFragment
import com.app.igrow.utils.*
import com.app.igrow.utils.Constants.COL_CAUSAL_AGENT
import com.app.igrow.utils.Constants.COL_CAUSAL_AGENT_FR
import com.app.igrow.utils.Constants.COL_CROP
import com.app.igrow.utils.Constants.COL_CROP_FR
import com.app.igrow.utils.Constants.COL_PART_AFFECTED
import com.app.igrow.utils.Constants.COL_PART_AFFECTED_FR
import com.app.igrow.utils.Constants.COL_PLANT_HEALTH_PROBLEM
import com.app.igrow.utils.Constants.COL_TYPE_OF_ENEMY
import com.app.igrow.utils.Constants.COL_TYPE_OF_ENEMY_FR
import com.app.igrow.utils.Constants.SHEET_DIAGNOSTIC
import com.app.igrow.utils.Utils.getLocalizeColumnName
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class DiagnoseFragment : BaseFragment<FragmentDiagnoseBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDiagnoseBinding
        get() = FragmentDiagnoseBinding::inflate

    private val viewModel: DiagnosticFragmentViewModel by viewModels()
    private lateinit var listDialog: Dialog
    private lateinit var dialogeLayoutBinding: DialogeLayoutBinding
    private lateinit var dialogListAdapter: DialogListAdapter
    private var diagnosticColumnName = ""
    private var dialogList = arrayListOf<String>()
    private var filteredList = arrayListOf<String>()
    private var diagnosticFiltersHashMap = HashMap<String, String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activateListener()
        activateObserver()

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.toHomePageFromDiagnostic)
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun activateListener() {
        try {

            binding.llCrop.setOnClickListener {
                diagnosticColumnName = getLocalizeColumnName(COL_CROP)
                viewModel.getDiagnosticColumnData(diagnosticFiltersHashMap, diagnosticColumnName, SHEET_DIAGNOSTIC)
            }
            binding.llPartAffected.setOnClickListener {
                diagnosticColumnName = getLocalizeColumnName(COL_PART_AFFECTED)
                viewModel.getDiagnosticColumnData(diagnosticFiltersHashMap, diagnosticColumnName, SHEET_DIAGNOSTIC)
            }
            binding.llCasualAgent.setOnClickListener {
                diagnosticColumnName = getLocalizeColumnName(COL_CAUSAL_AGENT)
                viewModel.getDiagnosticColumnData(diagnosticFiltersHashMap, diagnosticColumnName, SHEET_DIAGNOSTIC)
            }
            binding.llEnemyType.setOnClickListener {
                diagnosticColumnName = getLocalizeColumnName(COL_TYPE_OF_ENEMY)
                viewModel.getDiagnosticColumnData(diagnosticFiltersHashMap, diagnosticColumnName, SHEET_DIAGNOSTIC)
            }
            binding.btnSearch.setOnClickListener {
                if (binding.etSearch.text.toString().isNotEmpty()) {
                    diagnosticFiltersHashMap.clear()
                    diagnosticFiltersHashMap[getLocalizeColumnName(COL_PLANT_HEALTH_PROBLEM)] =
                        binding.etSearch.text.toString().trim()
                }
                viewModel.searchDiagnostic(diagnosticFiltersHashMap)

            }

            binding.etSearch.doAfterTextChanged { editable ->
                if (editable != null && (editable.isEmpty() || editable.isBlank())) {
                    diagnosticFiltersHashMap.remove(COL_PLANT_HEALTH_PROBLEM)
                }
            }

            binding.btnReset.setOnClickListener {
                resetAllFilters()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun activateObserver() {
        viewModel.uiStateLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is LoadingState -> {
                    binding.pbDiagnostic.visible()
                }
                is UnloadingState -> {
                    binding.pbDiagnostic.gone()

                }
                else -> {}
            }
        }
        viewModel.getDiagnosticColumnDataLiveData.observe(viewLifecycleOwner) {
            if (it != null && it.size > 0) {
                it.sort()
                if (diagnosticColumnName.isNotEmpty()) {
                    addPlaceholderInFilterList(it)
                }
                dialogList = it
                showListDialog(it)
            }
        }

        viewModel.filtersLiveData.observe(viewLifecycleOwner) { response ->
            if (response.isNotEmpty()) {
                val searchResultData =
                    SearchResult(diagnosticFiltersHashMap, response)
                val bundle =
                    bundleOf(ARG_RESULT_KEY to searchResultData)
                findNavController().navigate(R.id.toDiagnoseSearchResultFragment, bundle)
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


    private fun setupInitialData() {
        arguments?.let {
            if (it.isEmpty || it.containsKey(ARG_DIAGNOSE_INITIAL_DATA_KEY).not())
                return

            val argsItem = it.get(ARG_DIAGNOSE_INITIAL_DATA_KEY) as HashMap<*,*>
            argsItem.forEach { item ->
                diagnosticColumnName = item.key.toString()
                setSelectedValueToView(item.value.toString())
                populateFiltersObject(item.value.toString())
            }
        }
    }

    private fun addPlaceholderInFilterList(dataList: ArrayList<String>) {
        when (diagnosticColumnName) {
            COL_CROP, COL_CROP_FR -> {
                dataList.add(0, getString(R.string.all_crops))
                return
            }
            COL_TYPE_OF_ENEMY, COL_TYPE_OF_ENEMY_FR -> {
                dataList.add(0, getString(R.string.all_enemy_types))
                return
            }
            COL_PART_AFFECTED, COL_PART_AFFECTED_FR -> {
                dataList.add(0, getString(R.string.all_parts_affected))
                return
            }
            COL_CAUSAL_AGENT, COL_CAUSAL_AGENT_FR -> {
                dataList.add(0, getString(R.string.all_causal_agents))
                return
            }
        }
    }

    private fun setSelectedValueToView(value: String) {
        if (diagnosticColumnName.isNotEmpty()) {
            when (diagnosticColumnName) {
                COL_CROP, COL_CROP_FR -> {
                    binding.tvCropFilterText.text = ""
                    binding.tvCropFilterText.text = value
                    return
                }
                COL_TYPE_OF_ENEMY, COL_TYPE_OF_ENEMY_FR -> {
                    binding.tvTypeOfEnemyFilterText.text = ""
                    binding.tvTypeOfEnemyFilterText.text = value
                    return
                }
                COL_PART_AFFECTED, COL_PART_AFFECTED_FR -> {
                    binding.tvPartAffectedFilterText.text = ""
                    binding.tvPartAffectedFilterText.text = value
                    return
                }
                COL_CAUSAL_AGENT, COL_CAUSAL_AGENT_FR -> {
                    binding.tvCausalAgentFilterText.text = ""
                    binding.tvCausalAgentFilterText.text = value
                    return
                }
            }
        }
    }

    private fun showListDialog(dataList: ArrayList<String>) {
        listDialog = Dialog(requireContext())
        listDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        dialogeLayoutBinding = DialogeLayoutBinding.inflate(LayoutInflater.from(context))
        listDialog.setContentView(dialogeLayoutBinding.root)

        listDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogeLayoutBinding.rcListDialog.layoutManager = LinearLayoutManager(requireContext())
        dialogeLayoutBinding.rcListDialog.setHasFixedSize(true)

        setDialogListToAdapter(dataList)

        listDialog.show()


        dialogeLayoutBinding.etListDialogSearch.doAfterTextChanged { editable ->
            if (!editable.isNullOrEmpty() && editable.isNotBlank()) {
                val myResult = searchList(editable.toString())
                setDialogListToAdapter(myResult)
            } else {
                filteredList.clear()
                setDialogListToAdapter(dialogList)
            }
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
            Log.e(TAG, ex.stackTraceToString())
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
        dialogeLayoutBinding.rcListDialog.adapter = dialogListAdapter
    }

    private fun populateFiltersObject(value: String) {
        if (diagnosticColumnName.isNotEmpty() &&
            value != getString(R.string.all_crops) &&
            value != getString(R.string.all_enemy_types) &&
            value != getString(R.string.all_parts_affected) &&
            value != getString(R.string.all_causal_agents)
        ) {
            diagnosticFiltersHashMap[diagnosticColumnName] = value
        } else {
            diagnosticFiltersHashMap.remove(diagnosticColumnName)
        }
    }

    private fun resetAllFilters() {
        binding.etSearch.text?.clear()
        if (diagnosticFiltersHashMap.isNotEmpty()) {
            binding.tvCropFilterText.text = getString(R.string.crop)
            binding.tvTypeOfEnemyFilterText.text = getString(R.string.enemy_type)
            binding.tvPartAffectedFilterText.text = getString(R.string.part_affected)
            binding.tvCausalAgentFilterText.text = getString(R.string.causal_agent)
            diagnosticFiltersHashMap.clear()
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.filtersLiveData.value = arrayListOf()
        viewModel.getDiagnosticColumnDataLiveData.value = arrayListOf()

        viewModel.getDiagnosticColumnDataLiveData.removeObservers(viewLifecycleOwner)
        viewModel.filtersLiveData.removeObservers(viewLifecycleOwner)
    }

    override fun onResume() {
        super.onResume()
        setupInitialData()
        filteredList.clear()
    }

    companion object {
        const val TAG = " DiagnoseFragment"
        const val ARG_RESULT_KEY = "filters"
        const val ARG_SEARCH_RESULT_ITEM_KEY = "diagnostic_data"
        const val ARG_DIAGNOSE_DATA_KEY = "data"
        const val ARG_DIAGNOSE_INITIAL_DATA_KEY = "diagnose_initial_data"
    }

}