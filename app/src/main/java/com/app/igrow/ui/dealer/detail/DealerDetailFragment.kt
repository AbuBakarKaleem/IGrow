package com.app.igrow.ui.dealer.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.app.igrow.R
import com.app.igrow.base.BaseFragment
import com.app.igrow.data.model.sheets.Dealers
import com.app.igrow.data.model.sheets.Products
import com.app.igrow.databinding.FragmentDealerDetailBinding
import com.app.igrow.ui.dealer.alldealers.DealersListFragment.Companion.ARG_DEALER_DETAIL_ITEM_KEY
import com.app.igrow.ui.diagnose.DiagnoseFragment
import com.app.igrow.ui.products.detail.ProductDetailViewModel
import com.app.igrow.utils.Constants
import com.app.igrow.utils.Utils.isLocaleFrench
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DealerDetailFragment : BaseFragment<FragmentDealerDetailBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDealerDetailBinding
        get() = FragmentDealerDetailBinding::inflate

    private val viewModel: ProductDetailViewModel by viewModels()

    private var dealerInfo = Dealers()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val itemArgs = it.get(ARG_DEALER_DETAIL_ITEM_KEY) as Dealers
            dealerInfo = itemArgs
            setPopulateViews(itemArgs)
        }

        binding.tvTelephone.setOnClickListener {
            if ((it as TextView).text.toString().isEmpty().not())
                openDialer((it).text.toString().trim())
        }

        binding.tvMobile.setOnClickListener {
            if ((it as TextView).text.toString().isEmpty().not())
                openWhatsapp((it).text.toString().trim())
        }

        binding.btnHelp.setOnClickListener {
            findNavController().navigate(R.id.toHelpFragmentFromDealersDetail)
        }
        binding.tvDistributors.setOnClickListener {
            if (!binding.tvDistributors.text.isNullOrEmpty()) {
                val value: String =
                    if (isLocaleFrench()) Constants.COL_DISTRIBUTORS_NAME_FR else Constants.COL_DISTRIBUTORS_NAME
                viewModel.getDistributorByName(binding.tvDistributors.text.toString(), value)
            }
        }
        activateObserver()

    }

    private fun setPopulateViews(dealer: Dealers) {
        var value = if (isLocaleFrench()) dealer.dealer_name_fr else dealer.dealer_name
        binding.tvDealerName.text = value
        value = if (isLocaleFrench()) dealer.city_town_fr else dealer.city_town
        binding.tvCityTown.text = value
        value = if (isLocaleFrench()) dealer.region_fr else dealer.region
        binding.tvRegion.text = value
        value = if (isLocaleFrench()) dealer.telephone_fr else dealer.telephone
        binding.tvTelephone.text = value
        value = if (isLocaleFrench()) dealer.mobile_fr else dealer.mobile
        binding.tvMobile.text = value
        value = if (isLocaleFrench()) dealer.address_fr else dealer.address
        binding.tvAddress.text = value
        value = if (isLocaleFrench()) dealer.distributors_fr else dealer.distributors
        binding.tvDistributors.text = value
    }

    private fun openDialer(number: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$number")
        startActivity(intent)
    }

    private fun openWhatsapp(number: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("https://api.whatsapp.com/send?phone=$number")
        startActivity(intent)
    }

    private fun activateObserver() {
        viewModel.getDistributorByNameDataLiveData.observe(viewLifecycleOwner) {
            if (it.id.isNotEmpty()) {
                val itemBundle = bundleOf(
                    DiagnoseFragment.ARG_DIAGNOSE_DATA_KEY to it,
                    ARG_DISTRIBUTOR_NAVIGATION to true,
                    ARG_DEALER_INFO to dealerInfo,
                    DiagnoseFragment.ARG_SEARCH_RESULT_ITEM_KEY to Products()
                )
                findNavController().navigate(R.id.toDistributorDetailFragment, itemBundle)
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.no_data_found),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    companion object {
        const val ARG_DISTRIBUTOR_NAVIGATION = "ARG_DISTRIBUTOR_NAVIGATION"
        const val ARG_DEALER_INFO = "ARG_DEALER_INFO"
    }
}