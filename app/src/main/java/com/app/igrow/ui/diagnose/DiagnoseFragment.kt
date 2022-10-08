package com.app.igrow.ui.diagnose

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.igrow.adpters.DialogListAdapter
import com.app.igrow.base.BaseFragment
import com.app.igrow.databinding.DialogeLayoutBinding
import com.app.igrow.databinding.FragmentDiagnoseBinding
import com.app.igrow.ui.admin.LoadingState
import com.app.igrow.ui.admin.UnloadingState
import com.app.igrow.utils.Constants.COL_CAUSAL_AGENT
import com.app.igrow.utils.Constants.COL_CROP
import com.app.igrow.utils.Constants.COL_PART_AFFECTED
import com.app.igrow.utils.Constants.COL_TYPE_OF_ENEMY
import com.app.igrow.utils.Constants.SHEET_DIAGNOSTIC
import com.app.igrow.utils.gone
import com.app.igrow.utils.visible
import dagger.hilt.android.AndroidEntryPoint

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
    private var diagnosticFiltersHashMap = HashMap<String,String>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel*/

        activateListener()
        activateObserver()
    }

    private fun activateListener() {
        try {

            binding.llCrop.setOnClickListener {
                diagnosticColumnName = COL_CROP
                viewModel.getDiagnosticColumnData(COL_CROP, SHEET_DIAGNOSTIC)
            }
            binding.llPartAffected.setOnClickListener {
                diagnosticColumnName = COL_PART_AFFECTED
                viewModel.getDiagnosticColumnData(COL_PART_AFFECTED, SHEET_DIAGNOSTIC)
            }
            binding.llCasualAgent.setOnClickListener {
                diagnosticColumnName = COL_CAUSAL_AGENT
                viewModel.getDiagnosticColumnData(COL_CAUSAL_AGENT, SHEET_DIAGNOSTIC)
            }
            binding.llEnemyType.setOnClickListener {
                diagnosticColumnName = COL_TYPE_OF_ENEMY
                viewModel.getDiagnosticColumnData(COL_TYPE_OF_ENEMY, SHEET_DIAGNOSTIC)
            }
            binding.btnSearch.setOnClickListener {
                viewModel.searchDiagnostic(diagnosticFiltersHashMap)
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
            }
        }
        viewModel.getDiagnosticColumnDataLiveData.observe(viewLifecycleOwner) {
            if (it != null && it.size > 0) {
                dialogList = it
                showListDialog(it)
            }
        }
    }

    private fun setSelectedValueToView(value: String) {
        if (diagnosticColumnName.isNotEmpty()) {
            when (diagnosticColumnName) {
                COL_CROP -> {
                    binding.tvCropFilterText.text = ""
                    binding.tvCropFilterText.text = value
                    return
                }
                COL_TYPE_OF_ENEMY -> {
                    binding.tvTypeOfEnemyFilterText.text = ""
                    binding.tvTypeOfEnemyFilterText.text = value
                    return
                }
                COL_PART_AFFECTED -> {
                    binding.tvPartAffectedFilterText.text = ""
                    binding.tvPartAffectedFilterText.text = value
                    return
                }
                COL_CAUSAL_AGENT -> {
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

        dialogeLayoutBinding.rcListDialog.adapter = dialogListAdapter
        listDialog.show()
    }

    private fun setDialogListToAdapter(dataList: ArrayList<String>) {
        dialogListAdapter = DialogListAdapter { uiValue, position ->
            val selectedValue = dialogList[position]
            listDialog.dismiss()

            setSelectedValueToView(uiValue)
            populateFiltersObject(selectedValue)
        }

        dialogListAdapter.differ.submitList(dataList)
    }

    private fun populateFiltersObject(value:String){
        val englishAndFrenchValues = value.split(":")
        if(diagnosticColumnName.isNotEmpty()){
            diagnosticFiltersHashMap[diagnosticColumnName] = englishAndFrenchValues[0]
        }
    }
}