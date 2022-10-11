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

    private fun setPopulateViews(itemArgs: Diagnostic) {
        binding.tvProblemMain.text = itemArgs.plant_health_problem
        binding.tvMainCasualAgent.text = itemArgs.causal_agent
        binding.tvIdentificationTypeOfEnemy.text = itemArgs.type_of_enemy
        binding.tvIdentificationCrop.text = itemArgs.crop
        //binding.tvIdentificationCondition.text= itemArgs.crop
        //binding.tvSymptomsLeaves.text= itemArgs.symptoms_impact
        binding.tvSymptomsImpact.text = itemArgs.symptoms_impact
        //binding.tvBiologicalControl.text = itemArgs.control
        //tvBiologicalControl.text = itemArgs.control
        binding.tvCulturalControl.text = itemArgs.control

    }

    private fun activateListener() {
        binding.llControlProduct.setOnClickListener {
            findNavController().navigate(R.id.toProductsFragment)
        }
    }
}