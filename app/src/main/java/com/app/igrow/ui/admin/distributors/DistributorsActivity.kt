package com.app.igrow.ui.admin.distributors

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import com.app.igrow.R
import com.app.igrow.base.BaseActivity
import com.app.igrow.data.model.sheets.Distributors
import com.app.igrow.databinding.ActivityDistributorsBinding
import com.app.igrow.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DistributorsActivity : BaseActivity() {
    private lateinit var binding: ActivityDistributorsBinding
    private val viewModel: DistributorViewModel by viewModels()
    private var actionType = ActionType.EDIT.toString()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDistributorsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        actionType =
            intent.getStringExtra(ActionType.TYPE.toString()).toString()
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
        viewModel.getDistributorLiveData.observe(this) {
            if (it is String) {
                showMessage(it)
            } else if (it is Distributors) {
                populateDataOnViews(it)
            }
        }
        viewModel.updateDistributorLiveData.observe(this) {
            //Todo()
            val stringUtils = StringUtils(application)
            if (it.equals(stringUtils.updateSuccesMsg())) {
                reloadAllViews()
            }
            showMessage(it)
        }
        viewModel.deleteDistributorLiveData.observe(this) {
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
                    viewModel.getDistributor(binding.etId.text!!.trim().toString())
                }
            }
            if (it.text.equals(UpdateButtonText.Update.toString())) {
                updateDistributor()
            }
            if (it.text.equals(UpdateButtonText.Delete.toString())) {
                showDeleteConfirmationDialog()
            }
        }
        binding.ivBack.setOnClickListener { onBackPressed() }
    }

    private fun populateDataOnViews(Distributor: Distributors) {
        try {
            binding.etDistributorName.setText(Distributor.distributor_name)
            binding.etEmail.setText(Distributor.email)
            binding.etTelephone.setText(Distributor.telephone)
            binding.etWebsite.setText(Distributor.website)
            binding.etAddress.setText(Distributor.address)
            binding.etCityTown.setText(Distributor.city_town)
            binding.etRegion.setText(Distributor.region)
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
                deleteDistributor()
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

    private fun deleteDistributor() {
        if (binding.etId.text.toString().isNotEmpty()) {
            var updatedMap = getValuesFromViews()
            viewModel.deleteDistributor(binding.etId.text.toString().trim())
        }
    }

    private fun disableAllViews() {
        binding.etEmail.disable()
        binding.etTelephone.disable()
        binding.etWebsite.disable()
        binding.etFacebook.disable()
        binding.etDistributorName.disable()
        binding.etAddress.disable()
        binding.etCityTown.disable()
        binding.etRegion.disable()
        changeButtonText(getString(R.string.delete))
    }

    private fun hideViews() {
        binding.tlEmail.gone()
        binding.tlTelephone.gone()
        binding.tlWebsite.gone()
        binding.tlFacebook.gone()
        binding.tlDistributorName.gone()
        binding.tlAddress.gone()
        binding.tlCityTown.gone()
        binding.tlRegion.gone()
        binding.tvIdMessage.visible()
        changeButtonText(getString(R.string.search))
        binding.etId.isEnabled = true
        binding.etId.text?.clear()
    }

    private fun showViews() {
        binding.tlEmail.visible()
        binding.tlTelephone.visible()
        binding.tlWebsite.visible()
        binding.tlFacebook.visible()
        binding.tlAddress.visible()
        binding.tlCityTown.visible()
        binding.tlRegion.visible()
        binding.tlDistributorName.visible()

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

    private fun getValuesFromViews(): HashMap<String, Distributors> {
        val dataMap = HashMap<String, Distributors>()
        try {
            val distributor = Distributors(
                binding.etId.text.toString().trim(),
                binding.etDistributorName.text.toString().trim(),
                "",
                binding.etAddress.text.toString().trim(),
                "",
                binding.etCityTown.text.toString().trim(),
                "",
                binding.etRegion.text.toString().trim(),
                "",
                binding.etTelephone.text.toString().trim(),
                ""
            )
            dataMap[distributor.id] = distributor
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return dataMap
    }

    private fun updateDistributor() {
        try {
            val updatedMap = getValuesFromViews()
            viewModel.updateDistributor(updatedMap)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun reloadAllViews() {
        clearEditTexts()
        hideViews()
    }

    private fun clearEditTexts() {

        binding.etEmail.clear()
        binding.etTelephone.clear()
        binding.etWebsite.clear()
        binding.etFacebook.clear()
        binding.etAddress.clear()
        binding.etCityTown.clear()
        binding.etRegion.clear()
        binding.etDistributorName.clear()
    }

    enum class ActionType {
        EDIT, DELETE, TYPE
    }
}