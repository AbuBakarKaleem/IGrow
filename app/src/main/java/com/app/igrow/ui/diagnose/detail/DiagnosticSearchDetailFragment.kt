package com.app.igrow.ui.diagnose.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.app.igrow.R
import com.app.igrow.base.BaseFragment
import com.app.igrow.data.model.sheets.Diagnostic
import com.app.igrow.databinding.FragmentDiagnosticSearchDetailBinding
import com.app.igrow.ui.diagnose.DiagnoseFragment
import com.app.igrow.utils.Utils

class DiagnosticSearchDetailFragment : BaseFragment<FragmentDiagnosticSearchDetailBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDiagnosticSearchDetailBinding
        get() = FragmentDiagnosticSearchDetailBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val itemArgs = it.get(DiagnoseFragment.ARG_SEARCH_RESULT_ITEM_KEY) as Diagnostic
            setPopulateViews(itemArgs)
        }
        activateListener()
    }

    private fun setPopulateViews(model: Diagnostic) {

        var value = if(Utils.isLocaleFrench()) model.plant_health_problem_fr  else model.plant_health_problem
        binding.tvProblemMain.text = value

        value = if(Utils.isLocaleFrench()) model.causal_agent_fr  else model.causal_agent
        binding.tvMainCasualAgent.text = value

        value = if(Utils.isLocaleFrench()) model.type_of_enemy_fr  else model.type_of_enemy
        binding.tvIdentificationTypeOfEnemy.text = value

        value = if(Utils.isLocaleFrench()) model.crop_fr  else model.crop
        binding.tvIdentificationCrop.text = value

        value = if(Utils.isLocaleFrench()) model.part_affected_fr else model.part_affected
        binding.tvIdentificationCondition.text= value

        value = if(Utils.isLocaleFrench()) model.symptoms_impact_fr  else model.symptoms_impact
        binding.tvSymptomsImpact.text = value

        //binding.tvBiologicalControl.text = itemArgs.control
        //tvBiologicalControl.text = itemArgs.control
        value = if(Utils.isLocaleFrench()) model.control_fr  else model.control
        binding.tvControl.text = value

    }

    private fun activateListener() {
        binding.llControlProduct.setOnClickListener {
            findNavController().navigate(R.id.toProductsFragment)
        }

        binding.btnHelp.setOnClickListener {
            findNavController().navigate(R.id.toHelpFragmentFromDiagnoseDetailsFragment)
        }
    }
}