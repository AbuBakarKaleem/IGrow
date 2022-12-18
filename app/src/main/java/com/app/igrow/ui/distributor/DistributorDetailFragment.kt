package com.app.igrow.ui.distributor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.igrow.base.BaseFragment
import com.app.igrow.data.model.sheets.Distributors
import com.app.igrow.databinding.FragmentDistributorDetailBinding
import com.app.igrow.ui.diagnose.DiagnoseFragment
import com.app.igrow.utils.Utils

class DistributorDetailFragment : BaseFragment<FragmentDistributorDetailBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDistributorDetailBinding
        get() = FragmentDistributorDetailBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val itemArgs = it.get(DiagnoseFragment.ARG_DISTRIBUTOR_DATA_KEY) as Distributors
            setPopulateViews(itemArgs)
        }
    }

    private fun setPopulateViews(distributor: Distributors) {
        var value = if (Utils.isLocaleFrench()) distributor.distributor_name_fr else distributor.distributor_name
        binding.tvDistributorsName.text = value
        value = if (Utils.isLocaleFrench()) distributor.city_town_fr else distributor.city_town
        binding.tvCityTown.text = value
        value = if (Utils.isLocaleFrench()) distributor.region_fr else distributor.region
        binding.tvRegion.text = value
        binding.tvTelephone.text = distributor.telephone
        binding.tvMobile.text = distributor.telephone_2
        value = if (Utils.isLocaleFrench()) distributor.email_fr else distributor.email
        binding.tvEmail.text = value
        value = if (Utils.isLocaleFrench()) distributor.address_fr else distributor.address
        binding.tvAddress.text = value
        value = if (Utils.isLocaleFrench()) distributor.distributor_name_fr else distributor.distributor_name
        binding.tvDistributors.text = value
    }
}