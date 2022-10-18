package com.app.igrow.ui.admin.dealers

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import com.app.igrow.R
import com.app.igrow.base.BaseActivity
import com.app.igrow.data.model.sheets.Dealers
import com.app.igrow.databinding.DealerDetailsFormBinding
import com.app.igrow.utils.*
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DealersActivity : BaseActivity() {

    private lateinit var binding: DealerDetailsFormBinding
    private val viewModel: DealersViewModel by viewModels()
    private var actionType = ActionType.EDIT.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DealerDetailsFormBinding.inflate(layoutInflater)
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
        viewModel.getDealerLiveData.observe(this) {
            if (it is String) {
                showMessage(it)
            } else if (it is Dealers) {
                populateDataOnViews(it)
            }
        }
        viewModel.updateDealerLiveData.observe(this) {
            //Todo()
            val stringUtils = StringUtils(application)
            if (it.equals(stringUtils.updateSuccesMsg())) {
                reloadAllViews()
            }
            showMessage(it)
        }
        viewModel.deleteDealerLiveData.observe(this) {
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
                if (binding.etId.text?.isNotEmpty() == true) {
                    viewModel.getDealer(binding.etId.text!!.trim().toString())
                }
            }
            if (it.text.equals(UpdateButtonText.Update.toString())) {
                updateDealer()
            }
            if (it.text.equals(UpdateButtonText.Delete.toString())) {
                showDeleteConfirmationDialog()
            }
        }
//        binding.ivBack.setOnClickListener { onBackPressed() }
    }

    private fun populateDataOnViews(dealer: Dealers) {
        try {
            binding.etDealerName.setText(dealer.dealer_name)
            binding.etTelephone.setText(dealer.telephone)
            binding.etAddress.setText(dealer.address)
            binding.etDistributor.setText(dealer.distributors)
            binding.etCityTown.setText(dealer.city_town)
            binding.etMobile.setText(dealer.mobile)
            binding.etRegion.setText(dealer.region)
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
                deletedealer()
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

    private fun deletedealer() {
        if (binding.etId.text.toString().isNotEmpty()) {
            viewModel.deleteDealer(binding.etId.text.toString().trim())
        }
    }

    private fun disableAllViews() {
        binding.etDealerName.disable()
        binding.etTelephone.disable()
        binding.etAddress.disable()
        binding.etCityTown.disable()
        binding.etRegion.disable()
        binding.etMobile.disable()
        binding.etDistributor.disable()
        binding.tvIdMessage.gone()
        changeButtonText(getString(R.string.delete))
    }

    private fun hideViews() {
        binding.tlDealerName.gone()
        binding.tlTelephone.gone()
        binding.tlAddress.gone()
        binding.tlCityTown.gone()
        binding.tlRegion.gone()
        binding.tlMobile.gone()
        binding.tlDistributor.gone()
        binding.tvIdMessage.visible()
        changeButtonText(getString(R.string.search))
        binding.etId.isEnabled = true
        binding.etId.text?.clear()
    }

    private fun showViews() {
        binding.tlDealerName.visible()
        binding.tlTelephone.visible()
        binding.tlAddress.visible()
        binding.tlCityTown.visible()
        binding.tlRegion.visible()
        binding.tlMobile.visible()
        binding.tlDistributor.visible()
        binding.tvIdMessage.gone()
        changeButtonText(getString(R.string.update))
        binding.etId.isEnabled = false
    }

    private fun changeButtonText(value: String) {
        binding.btnUpdate.text = value
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun getValuesFromViews():HashMap<String,Dealers>{
        val dataMap=HashMap<String,Dealers>()
        try {
            val dealer = Dealers(
                id = binding.etId.text.toString().trim(),
                dealer_name = binding.etDealerName.text.toString().trim(),
                dealer_name_fr = "",
                address = binding.etAddress.text.toString().trim(),
                address_fr =  "",
                city_town = binding.etCityTown.text.toString().trim(),
                city_town_fr = "",
                region = binding.etRegion.text.toString().trim(),
                region_fr = "",
                telephone = binding.etTelephone.text.toString().trim(),
                telephone_fr = "",
                mobile = binding.etMobile.text.toString(),
                mobile_fr = "",
                distributors = binding.etDistributor.text.toString(),
                distributors_fr = ""
            )
            dataMap[dealer.id] = dealer
        }catch (e:Exception){
            e.printStackTrace()
        }
        return dataMap
    }
    private fun updateDealer(){
        try {
            val updatedMap=getValuesFromViews()
            viewModel.updateDealer(updatedMap)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun reloadAllViews() {
        clearEditTexts()
        hideViews()
    }

    private fun clearEditTexts() {

        binding.etMobile.clear()
        binding.etDistributor.clear()
        binding.etRegion.clear()
        binding.etTelephone.clear()
        binding.etAddress.clear()
        binding.etCityTown.clear()
        binding.etDealerName.clear()
    }

    enum class ActionType {
        EDIT, DELETE, TYPE
    }
}
