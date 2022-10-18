package com.app.igrow.ui.admin.product

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.app.igrow.R
import com.app.igrow.data.model.sheets.Products
import com.app.igrow.databinding.ProductDetailsFormBinding
import com.app.igrow.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.product_details_form.*


@AndroidEntryPoint
class ProductsActivity : AppCompatActivity() {

    private lateinit var binding: ProductDetailsFormBinding
    private val viewModel: ProductsViewModel by viewModels()
    private var actionType = ActionType.EDIT.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProductDetailsFormBinding.inflate(layoutInflater)
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
        viewModel.getProductLiveData.observe(this) {
            if (it is String) {
                showMessage(it)
            } else if (it is Products) {
                populateDataOnViews(it)
            }
        }
        viewModel.updateProductLiveData.observe(this) {
            //Todo()
            val stringUtils = StringUtils(application)
            if (it.equals(stringUtils.updateSuccesMsg())) {
                reloadAllViews()
            }
            showMessage(it)
        }
        viewModel.deleteProductLiveData.observe(this) {
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
                    viewModel.getProduct(binding.etId.text!!.trim().toString())
                }
            }
            if (it.text.equals(UpdateButtonText.Update.toString())) {
                updateproduct()
            }
            if (it.text.equals(UpdateButtonText.Delete.toString())) {
                showDeleteConfirmationDialog()
            }
        }
//        binding.ivBack.setOnClickListener { onBackPressed() }
    }

    private fun populateDataOnViews(product: Products) {
        try {
            binding.etProductCategory.setText(product.product_name)
            binding.etProductName.setText(product.product_name)
            binding.etDistributor.setText(product.distributor)
            binding.etComposition.setText(product.composition)
            binding.etActiveIngredient.setText(product.active_ingredient)
            binding.etApplicationRate.setText(product.application_rate)
            binding.etFormulation.setText(product.formulation)
            binding.etEnemy.setText(product.enemy)
            binding.etCrop.setText(product.crop)
            binding.etTypeOfEnemy.setText(product.type_of_enemy)
            binding.etRegistrationNumber.setText(product.registration_number)
            binding.etToxicologicalClass.setText(product.toxicological_class)
            binding.etPackaging.setText(product.packaging_available)
            binding.etTreatmentTime.setText(product.treatment_time)
            binding.etRestrictionsOfUse.setText(product.restrictions_of_use)
            binding.etMethodOfApplication.setText(product.method_of_application)
            binding.etReEntryPeriod.setText(product.re_entry_period)
            binding.etTimeBeforeHarvest.setText(product.time_before_harvest)
            binding.etFrequencyOfApplication.setText(product.frequency_of_application)
            binding.etModeOfAction.setText(product.mode_of_action)
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
            alertBuilder.setMessage(getString(R.string.delete_confim_message))
            alertBuilder.setCancelable(true)

            alertBuilder.setPositiveButton(
                getString(R.string.yes)
            ) { dialog, _ ->
                deleteproduct()
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

    private fun deleteproduct() {
        if (binding.etId.text.toString().isNotEmpty()) {
            viewModel.deleteProduct(binding.etId.text.toString().trim())
        }
    }

    private fun disableAllViews() {
        binding.etProductName.disable()
        binding.etProductCategory.disable()
        binding.etApplicationRate.disable()
        binding.etDistributor.disable()
        binding.etActiveIngredient.disable()
        binding.etEnemy.disable()
        binding.etReEntryPeriod.disable()
        binding.etRegistrationNumber.disable()
        binding.etTreatmentTime.disable()
        binding.etRestrictionsOfUse.disable()
        binding.etToxicologicalClass.disable()
        binding.etCrop.disable()
        binding.etFormulation.disable()
        binding.etComposition.disable()
        binding.etPackaging.disable()
        binding.etFrequencyOfApplication.disable()
        binding.etMethodOfApplication.disable()
        binding.etTypeOfEnemy.disable()
        binding.etTimeBeforeHarvest.disable()
        binding.etFormulation.disable()
        binding.etModeOfAction.disable()
        binding.tvIdMessage.gone()
        changeButtonText(getString(R.string.delete))
    }

    private fun hideViews() {
        binding.tlProductName.gone()
        binding.tlProductCategory.gone()
        binding.tlApplicationRate.gone()
        binding.tlDistributor.gone()
        binding.tlActiveIngredient.gone()
        binding.tlEnemy.gone()
        binding.tlReEntryPeriod.gone()
        binding.tlRegistrationNumber.gone()
        binding.tlTreatmentTime.gone()
        binding.tlTypeOfEnemy.gone()
        binding.tlCrop.gone()
        binding.tlRestrictionsOfUse.gone()
        binding.tlToxicologicalClass.gone()
        binding.tlTimeBeforeHarvest.gone()
        binding.tlFormulation.gone()
        binding.tlComposition.gone()
        binding.tlPackaging.gone()
        binding.tlFrequencyOfApplication.gone()
        binding.tlMethodOfApplication.gone()
        binding.tlModeOfAction.gone()
        binding.tvIdMessage.visible()
        changeButtonText(getString(R.string.search))
        binding.etId.isEnabled = true
        binding.etId.text?.clear()
    }

    private fun showViews() {
        binding.tlProductName.visible()
        binding.tlProductCategory.visible()
        binding.tlApplicationRate.visible()
        binding.tlDistributor.visible()
        binding.tlActiveIngredient.visible()
        binding.tlEnemy.visible()
        binding.tlReEntryPeriod.visible()
        binding.tlRegistrationNumber.visible()
        binding.tlTreatmentTime.visible()
        binding.tlRestrictionsOfUse.visible()
        binding.tlToxicologicalClass.visible()
        binding.tlTimeBeforeHarvest.visible()
        binding.tlComposition.visible()
        binding.tlPackaging.visible()
        binding.tlFrequencyOfApplication.visible()
        binding.tlMethodOfApplication.visible()
        binding.tlFormulation.visible()
        binding.tlCrop.visible()
        binding.tlTypeOfEnemy.visible()
        binding.tlModeOfAction.visible()
        binding.tvIdMessage.visible()
        changeButtonText(getString(R.string.update))
        binding.etId.isEnabled = false
    }

    private fun changeButtonText(value: String) {
        binding.btnUpdate.text = value
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun getValuesFromViews():HashMap<String,Products>{
        val dataMap=HashMap<String,Products>()
        try {
            val product = Products(
                id = binding.etId.text.toString().trim(),
                crop = binding.etCrop.text.toString().trim(),
                product_name = binding.etProductName.text.toString().trim(),
                enemy = binding.etEnemy.text.toString().trim(),
                type_of_enemy = binding.etTypeOfEnemy.text.toString().trim(),
                product_category = binding.etProductCategory.text.toString().trim(),
                registration_number = binding.etRegistrationNumber.text.toString().trim(),
                active_ingredient = binding.etActiveIngredient.text.toString().trim(),
                composition = binding.etComposition.text.toString().trim(),
                formulation = binding.etFormulation.text.toString().trim(),
                toxicological_class = binding.etToxicologicalClass.text.toString().trim(),
                mode_of_action = binding.etModeOfAction.text.toString().trim(),
                packaging_available =  binding.etPackaging.text.toString().trim(),
                application_rate = binding.etApplicationRate.text.toString().trim(),
                treatment_time = binding.etTreatmentTime.text.toString().trim(),
                frequency_of_application = binding.etFrequencyOfApplication.text.toString().trim(),
                method_of_application = binding.etMethodOfApplication.text.toString().trim(),
                restrictions_of_use = binding.etRestrictionsOfUse.text.toString().trim(),
                re_entry_period = binding.etReEntryPeriod.text.toString().trim(),
                time_before_harvest = binding.etTimeBeforeHarvest.text.toString().trim(),
                distributor = binding.etDistributor.text.toString().trim()
            )
            dataMap[product.id] = product
        }catch (e:Exception){
            e.printStackTrace()
        }
        return dataMap
    }
    private fun updateproduct(){
        try {
            val updatedMap=getValuesFromViews()
            viewModel.updateProduct(updatedMap)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun reloadAllViews() {
        clearEditTexts()
        hideViews()
    }

    private fun clearEditTexts() {
        binding.etProductName.clear()
        binding.etProductCategory.clear()
        binding.etApplicationRate.clear()
        binding.etDistributor.clear()
        binding.etActiveIngredient.clear()
        binding.etEnemy.clear()
        binding.etCrop.clear()
        binding.etTypeOfEnemy.clear()
        binding.etReEntryPeriod.clear()
        binding.etRegistrationNumber.clear()
        binding.etTreatmentTime.clear()
        binding.etRestrictionsOfUse.clear()
        binding.etToxicologicalClass.clear()
        binding.etTimeBeforeHarvest.clear()
        binding.etFormulation.clear()
        binding.etModeOfAction.clear()
        binding.tvIdMessage.gone()
    }

    enum class ActionType {
        EDIT, DELETE, TYPE
    }
}
