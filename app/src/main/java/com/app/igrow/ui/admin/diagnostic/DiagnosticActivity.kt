package com.app.igrow.ui.admin.diagnostic

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import com.app.igrow.R
import com.app.igrow.base.BaseActivity
import com.app.igrow.data.model.sheets.Diagnostic
import com.app.igrow.databinding.ActivityEditDiagnosticBinding
import com.app.igrow.utils.*
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DiagnosticActivity : BaseActivity() {
    private lateinit var binding: ActivityEditDiagnosticBinding
    private val viewModel: DiagnosticActivityViewModel by viewModels()
    private var actionType = ActionType.EDIT.toString()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditDiagnosticBinding.inflate(layoutInflater)
        setContentView(binding.root)
        actionType = intent.getStringExtra(ActionType.TYPE.toString()).toString()
        initViews()
    }

    enum class UpdateButtonText {
        Search, Update, Delete
    }

    private fun initViews() {
        hideViews()
        activateListeners()
        activateObservers()

    }

    private fun activateObservers() {
        viewModel.getDiagnosticLiveData.observe(this) {
            if (it is String) {
                showMessage(it)
            } else if (it is Diagnostic) {
                populateDataOnViews(it)
            }
        }
        viewModel.updateDiagnosticLiveData.observe(this) {
            //Todo()
            val stringUtils = StringUtils(application)
            if (it.equals(stringUtils.updateSuccesMsg())) {
                reloadAllViews()
            }
            showMessage(it)
        }
        viewModel.deleteDiagnosticLiveData.observe(this) {
            //Todo()
            val stringUtils = StringUtils(application)
            if (it.equals(stringUtils.deleteSuccessMsg())) {
                reloadAllViews()
            }
            showMessage(it)
        }
    }

    private fun activateListeners() {
        binding.btnUpdate.setOnClickListener {
            if ((it as Button).text.toString() == UpdateButtonText.Search.toString()) {
                if (binding.etDiagnosticId.text?.isNotEmpty() == true) {
                    viewModel.getDiagnostic(binding.etDiagnosticId.text!!.trim().toString())
                }
            }
            if (it.text.equals(UpdateButtonText.Update.toString())) {
                updateDiagnostic()
            }
            if (it.text.equals(UpdateButtonText.Delete.toString())) {
                showDeleteConfirmationDialog()
            }
        }
        binding.ivBack.setOnClickListener { onBackPressed() }
    }

    private fun populateDataOnViews(diagnostic: Diagnostic) {
        try {
            binding.etCropName.setText(diagnostic.crop)
            binding.etPlantHealthProblem.setText(diagnostic.plant_health_problem)
            binding.etEnemyType.setText(diagnostic.type_of_enemy)
            binding.etCausalAgent.setText(diagnostic.causal_agent)
            binding.etPartAffected.setText(diagnostic.part_affected)
            binding.etControl.setText(diagnostic.control)
            binding.etSymptomsImpact.setText(diagnostic.symptoms_impact)
            showViews()
            if (actionType == ActionType.DELETE.toString()) {
                disableAllViews()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun showDeleteConfirmationDialog() {
        try {
            val alertBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
            alertBuilder.setMessage(getString(R.string.delete_confirm_message))
            alertBuilder.setCancelable(true)

            alertBuilder.setPositiveButton(
                getString(R.string.yes)
            ) { dialog, _ ->
                deleteDiagnostic()
                dialog.dismiss()
            }

            alertBuilder.setNegativeButton(
                getString(R.string.no)
            ) { dialog, _ -> dialog.cancel() }

            val alert11: AlertDialog = alertBuilder.create()
            alert11.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun deleteDiagnostic() {
        if (binding.etDiagnosticId.text.toString().isNotEmpty()) {
            var updatedMap=getValuesFromViews()
            viewModel.deleteDiagnostic(binding.etDiagnosticId.text.toString().trim(),updatedMap)
        }
    }

    private fun disableAllViews() {
        binding.etCropName.disable()
        binding.etEnemyType.disable()
        binding.etPlantHealthProblem.disable()
        binding.etCausalAgent.disable()
        binding.etPartAffected.disable()
        binding.etControl.disable()
        binding.etSymptomsImpact.disable()
        changeButtonText(getString(R.string.delete))
    }

    private fun hideViews() {
        binding.tlCropName.gone()
        binding.tlEnemyType.gone()
        binding.tlPlantHealthProblem.gone()
        binding.tlCausalAgent.gone()
        binding.tlPartAffected.gone()
        binding.tlControl.gone()
        binding.tlSymptomsImpact.gone()
        binding.tvIdMessage.visible()
        changeButtonText(getString(R.string.search))
        binding.etDiagnosticId.isEnabled = true
        binding.etDiagnosticId.text?.clear()
    }

    private fun showViews() {
        binding.tlCropName.visible()
        binding.tlPlantHealthProblem.visible()
        binding.tlEnemyType.visible()
        binding.tlCausalAgent.visible()
        binding.tlPartAffected.visible()
        binding.tlControl.visible()
        binding.tlSymptomsImpact.visible()
        binding.tvIdMessage.gone()
        changeButtonText(getString(R.string.update))
        binding.etDiagnosticId.isEnabled = false
    }

    private fun changeButtonText(value: String) {
        binding.btnUpdate.text = value
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun getValuesFromViews():HashMap<String,Diagnostic>{
        var dataMap=HashMap<String,Diagnostic>()
        try {
            val diagnostic = Diagnostic(
                binding.etDiagnosticId.text.toString().trim(),
                binding.etCropName.text.toString().trim(),
                "",
                binding.etEnemyType.text.toString().trim(),
                "",
                binding.etPlantHealthProblem.text.toString().trim(),
                "",
                binding.etCausalAgent.text.toString().trim(),
                "",
                binding.etPartAffected.text.toString().trim(),
                "", binding.etSymptomsImpact.text.toString(),
                "",
                binding.etControl.text.toString(),
                ""
            )
            dataMap[diagnostic.id] = diagnostic
        }catch (e:Exception){
            e.printStackTrace()
        }
        return dataMap
    }
    private fun updateDiagnostic(){
        try {
            val updatedMap=getValuesFromViews()
            viewModel.updateDiagnostic(updatedMap)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun reloadAllViews() {
        clearEditTexts()
        hideViews()
    }

    private fun clearEditTexts() {

        binding.etCropName.clear()
        binding.etEnemyType.clear()
        binding.etPlantHealthProblem.clear()
        binding.etCausalAgent.clear()
        binding.etPartAffected.clear()
        binding.etControl.clear()
        binding.etSymptomsImpact.clear()
    }

    enum class ActionType {
        EDIT, DELETE, TYPE
    }
}
