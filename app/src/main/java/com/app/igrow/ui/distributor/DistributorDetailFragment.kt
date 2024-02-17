package com.app.igrow.ui.distributor

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.app.igrow.IGrowApp
import com.app.igrow.R
import com.app.igrow.base.BaseFragment
import com.app.igrow.data.model.sheets.Dealers
import com.app.igrow.data.model.sheets.Distributors
import com.app.igrow.data.model.sheets.Products
import com.app.igrow.databinding.FragmentDistributorDetailBinding
import com.app.igrow.ui.dealer.alldealers.DealersListFragment
import com.app.igrow.ui.dealer.detail.DealerDetailFragment
import com.app.igrow.ui.dealer.detail.DealerDetailFragment.Companion.ARG_DEALER_INFO
import com.app.igrow.ui.diagnose.DiagnoseFragment
import com.app.igrow.utils.Utils

class DistributorDetailFragment : BaseFragment<FragmentDistributorDetailBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDistributorDetailBinding
        get() = FragmentDistributorDetailBinding::inflate

    private var productItem =  Products()
    private var dealerInfo = Dealers()
    private var website =  "http://www.agricadvisors.com/"

    @SuppressLint("QueryPermissionsNeeded")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val itemArgs = it.get(DiagnoseFragment.ARG_DIAGNOSE_DATA_KEY) as Distributors
            productItem = it.get(DiagnoseFragment.ARG_SEARCH_RESULT_ITEM_KEY) as Products
            website = itemArgs.website

            if (it.containsKey(ARG_DEALER_INFO)) {
                dealerInfo = it.get(ARG_DEALER_INFO) as Dealers
            }
            setPopulateViews(itemArgs)
        }

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    arguments?.let {
                        if (it.containsKey(DealerDetailFragment.ARG_DISTRIBUTOR_NAVIGATION)) {
                            val itemBundle = bundleOf(DealersListFragment.ARG_DEALER_DETAIL_ITEM_KEY to dealerInfo)
                            findNavController().navigate(R.id.distributorDetailToDealersDetailsFragment,itemBundle)
                        } else {
                            val itemBundle = bundleOf(DiagnoseFragment.ARG_SEARCH_RESULT_ITEM_KEY to productItem)
                            findNavController().navigate(R.id.distributorDetailToProductsFragment,itemBundle)
                        }
                    }
                  }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)


        binding.btnWebsite.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(website))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        binding.cvTelephone.setOnClickListener {
            if (binding.tvTelephone.text.toString().isNotEmpty())
                openDialer(binding.tvTelephone.text.toString())
        }
        binding.cvMobileNumber.setOnClickListener {
            if (binding.tvMobile.text.toString().isNotEmpty())
                openWhatsapp(binding.tvMobile.text.toString())
        }

        binding.btnShop.setOnClickListener {
            arguments?.let {
                if (it.containsKey(DealerDetailFragment.ARG_DISTRIBUTOR_NAVIGATION)) {
                    val itemBundle = bundleOf(DealersListFragment.ARG_DEALER_DETAIL_ITEM_KEY to dealerInfo)
                    findNavController().navigate(R.id.distributorDetailToDealersFragment,itemBundle)
                } else {
                    findNavController().navigate(R.id.distributorDetailToDealersFragment)
                }
            }
        }

        binding.tvEmail.setOnClickListener {
            if (binding.tvEmail.text.trim().isNotEmpty()){
                val email = binding.tvEmail.text.trim().toString()

                val emailIntent = Intent(Intent.ACTION_SEND)
                emailIntent.type = "plain/text"
                emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "")
                emailIntent.putExtra(Intent.EXTRA_TEXT, email)
                // Check if the device has an app that can handle this intent
                if(emailIntent.resolveActivity(IGrowApp.getInstance().packageManager) != null){
                    startActivity(emailIntent)
                } else {
                    Toast.makeText(IGrowApp.getInstance(),
                        R.string.no_email_app_found,
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setPopulateViews(distributor: Distributors) {
        var value =
            if (Utils.isLocaleFrench()) distributor.distributor_name_fr else distributor.distributor_name
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
    }

    private fun openWhatsapp(number: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("https://api.whatsapp.com/send?phone=$number")
        startActivity(intent)
    }

    private fun openDialer(number: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$number")
        startActivity(intent)
    }
}