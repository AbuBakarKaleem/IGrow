package com.app.igrow.ui.diagnose.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.app.igrow.R
import com.app.igrow.adpters.DiseaseImagesAdapter
import com.app.igrow.base.BaseFragment
import com.app.igrow.data.model.sheets.Diagnostic
import com.app.igrow.databinding.FragmentDiagnosticSearchDetailBinding
import com.app.igrow.ui.diagnose.DiagnoseFragment
import com.app.igrow.utils.Utils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

class DiagnosticSearchDetailFragment : BaseFragment<FragmentDiagnosticSearchDetailBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDiagnosticSearchDetailBinding
        get() = FragmentDiagnosticSearchDetailBinding::inflate

    private var diagnostic = Diagnostic()
    private val diseaseImagesAdapter = DiseaseImagesAdapter(emptyList())


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val itemArgs = it.get(DiagnoseFragment.ARG_SEARCH_RESULT_ITEM_KEY) as Diagnostic
            diagnostic =  itemArgs
            setPopulateViews(itemArgs)
        }
        // Initialize data.
//        val myDataset = loadImagesDataSet()
//        binding.rvDiseases.adapter = diseaseImagesAdapter
//        binding.rvDiseases.setHasFixedSize(true)
//        diseaseImagesAdapter.differ.submitList(myDataset)
//        diseaseImagesAdapter.notifyDataSetChanged()

        activateListener()
    }

//    private fun loadImagesDataSet(): List<DiagnosticImageData> {
////        return listOf<DiagnosticImageData>(
////            DiagnosticImageData(R.drawable.disease_one),
////            DiagnosticImageData(R.drawable.disease_two),
////            DiagnosticImageData(R.drawable.disease_three)
////        )
//    }

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

        Glide.with(this)
            .load(model.image_sample)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .apply(
                RequestOptions()
                    .error(R.drawable.ic_broken_image)
            ).override(Target.SIZE_ORIGINAL)
            .into(binding.ivImageSample)

        value = if(Utils.isLocaleFrench()) model.control_fr  else model.control
        binding.tvControl.text = value

    }

    private fun activateListener() {
        binding.llControlProduct.setOnClickListener {
            val itemBundle = bundleOf(DiagnoseFragment.ARG_SEARCH_RESULT_ITEM_KEY to diagnostic)
            findNavController().navigate(R.id.toProductsFragment,itemBundle)
        }

        binding.btnHelp.setOnClickListener {
            findNavController().navigate(R.id.toHelpFragmentFromDiagnoseDetailsFragment)
        }
    }
}