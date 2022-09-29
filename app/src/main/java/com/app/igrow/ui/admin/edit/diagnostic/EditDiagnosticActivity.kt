package com.app.igrow.ui.admin.edit.diagnostic

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.app.igrow.R
import com.app.igrow.data.model.sheets.Diagnostic
import com.app.igrow.databinding.ActivityEditDiagnosticBinding
import com.app.igrow.utils.gone
import com.app.igrow.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditDiagnosticActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditDiagnosticBinding
    private val viewModel: EditDiagnosticViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditDiagnosticBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
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
    }

    private fun activateListeners() {
        binding.btnUpdate.setOnClickListener {
            if (binding.etDiagnosticId.text?.isNotEmpty() == true) {
                viewModel.getDiagnostic(binding.etDiagnosticId.text!!.trim().toString())
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
            binding.etDiagnosticId.isEnabled = false
            changeButtonText(getString(R.string.update))
            showViews()
        } catch (e: Exception) {
            e.printStackTrace()
        }

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
    }

    private fun changeButtonText(value: String) {
        binding.btnUpdate.text = value
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

}