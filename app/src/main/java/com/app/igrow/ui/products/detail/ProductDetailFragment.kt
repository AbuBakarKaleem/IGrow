package com.app.igrow.ui.products.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.app.igrow.R
import com.app.igrow.base.BaseFragment
import com.app.igrow.data.model.sheets.Products
import com.app.igrow.databinding.FragmentProductDetailBinding
import com.app.igrow.ui.diagnose.DiagnoseFragment
import com.app.igrow.utils.Constants.COL_DISTRIBUTORS_NAME
import com.app.igrow.utils.Constants.COL_DISTRIBUTORS_NAME_FR
import com.app.igrow.utils.Utils.isLocaleFrench
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailFragment : BaseFragment<FragmentProductDetailBinding>() {

    private val viewModel: ProductDetailViewModel by viewModels()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentProductDetailBinding
        get() = FragmentProductDetailBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val itemArgs = it.get(DiagnoseFragment.ARG_SEARCH_RESULT_ITEM_KEY) as Products
            setPopulateViews(itemArgs)
        }
        activateListener()
        activateObserver()
    }

    private fun setPopulateViews(product: Products) {
        var value = if (isLocaleFrench()) product.product_name_fr else product.product_name
        binding.tvProductName.text = value
        value = if (isLocaleFrench()) product.composition_fr else product.composition
        binding.tvActiveAgent.text = value
        value = if (isLocaleFrench()) product.distributor_fr else product.distributor
        binding.tvDealer.text = value
        value =
            if (isLocaleFrench()) product.registration_number_fr else product.registration_number
        binding.tvRegistrationNumber.text = value
        value = if (isLocaleFrench()) product.active_ingredient_fr else product.active_ingredient
        binding.tvActiveIngrident.text = value
        value = if (isLocaleFrench()) product.formulation_fr else product.formulation
        binding.tvFormulation.text = value
        value =
            if (isLocaleFrench()) product.toxicological_class_fr else product.toxicological_class
        binding.tvToxicologicalClass.text = value
        value = if (isLocaleFrench()) product.mode_of_action_fr else product.mode_of_action
        binding.tvModeOfAction.text = value
        value =
            if (isLocaleFrench()) product.packaging_available_fr else product.packaging_available
        binding.tvPackaging.text = value
        value = if (isLocaleFrench()) product.crop_fr else product.crop
        binding.tvCrop.text = value
        value = if (isLocaleFrench()) product.treatment_time_fr else product.treatment_time
        binding.tvTreatmentTime.text = value
        value = if (isLocaleFrench()) product.type_of_enemy_fr else product.type_of_enemy
        binding.tvEnemyType.text = value
        value = if (isLocaleFrench()) product.application_rate_fr else product.application_rate
        binding.tvApplicationRate.text = value
        value =
            if (isLocaleFrench()) product.restrictions_of_use_fr else product.restrictions_of_use
        binding.tvRestrictionsOfUse.text = value
        value =
            if (isLocaleFrench()) product.method_of_application_fr else product.method_of_application
        binding.tvMethodOfApplication.text = value
        value = if (isLocaleFrench()) product.re_entry_period_fr else product.re_entry_period
        binding.tvReEntryPeriod.text = value
        value =
            if (isLocaleFrench()) product.time_before_harvest_fr else product.time_before_harvest
        binding.tvTimeBeforeHarvest.text = value
        value =
            if (isLocaleFrench()) product.frequency_of_application_fr else product.frequency_of_application
        binding.tvFrequencyOfApplication.text = value
    }

    private fun activateListener() {
        binding.llShop.setOnClickListener {
            findNavController().navigate(R.id.toDealerFragment)
        }

        binding.tvDealer.setOnClickListener {
            val value: String =
                if (isLocaleFrench()) COL_DISTRIBUTORS_NAME_FR else COL_DISTRIBUTORS_NAME
            viewModel.getDistributorByName(binding.tvDealer.text.toString().trim(), value)
        }
    }

    private fun activateObserver() {
        viewModel.getDistributorByNameDataLiveData.observe(viewLifecycleOwner) {
            if (it.id.isNotEmpty()) {
                moveToDistributorScreen()
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.no_data_found),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun moveToDistributorScreen() {

        Toast.makeText(requireContext(), "Data received ", Toast.LENGTH_LONG).show()

    }
}