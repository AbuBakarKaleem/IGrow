package com.app.igrow.ui.dealer.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.app.igrow.base.BaseFragment
import com.app.igrow.data.model.sheets.Dealers
import com.app.igrow.databinding.FragmentDealerDetailBinding
import com.app.igrow.ui.dealer.alldealers.DealersListFragment.Companion.ARG_DEALER_DETAIL_ITEM_KEY
import com.app.igrow.utils.Utils.isLocaleFrench

class DealerDetailFragment : BaseFragment<FragmentDealerDetailBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDealerDetailBinding
        get() = FragmentDealerDetailBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val itemArgs = it.get(ARG_DEALER_DETAIL_ITEM_KEY) as Dealers
            setPopulateViews(itemArgs)
        }

        binding.tvTelephone.setOnClickListener {
            if ((it as TextView).text.toString().isEmpty().not())
                openDialer((it).text.toString().trim())
        }

        binding.tvMobile.setOnClickListener {
            if ((it as TextView).text.toString().isEmpty().not())
                openDialer((it).text.toString().trim())
        }
    }

    private fun setPopulateViews(dealer: Dealers) {
        var value = if(isLocaleFrench()) dealer.dealer_name_fr  else dealer.dealer_name
        binding.tvDealerName.text = value
        value = if(isLocaleFrench()) dealer.city_town_fr  else dealer.city_town
        binding.tvCityTown.text = value
        value = if(isLocaleFrench()) dealer.region_fr  else dealer.region
        binding.tvRegion.text = value
        value = if(isLocaleFrench()) dealer.telephone_fr  else dealer.telephone
        binding.tvTelephone.text = value
        value = if(isLocaleFrench()) dealer.mobile_fr  else dealer.mobile
        binding.tvMobile.text = value
        value = if(isLocaleFrench()) dealer.address_fr  else dealer.address
        binding.tvAddress.text = value
        value = if(isLocaleFrench()) dealer.distributors_fr  else dealer.distributors
        binding.tvDistributors.text = value
    }

    private fun openDialer(number:String){
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$number")
        startActivity(intent)
    }

}