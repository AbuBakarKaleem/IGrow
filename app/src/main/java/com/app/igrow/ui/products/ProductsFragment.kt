package com.app.igrow.ui.products

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.igrow.R
import com.app.igrow.adpters.DialogListAdapter
import com.app.igrow.base.BaseFragment
import com.app.igrow.data.model.detail.SearchResult
import com.app.igrow.data.model.sheets.Diagnostic
import com.app.igrow.databinding.DialogeLayoutBinding
import com.app.igrow.databinding.FragmentProductsBinding
import com.app.igrow.ui.admin.LoadingState
import com.app.igrow.ui.admin.UnloadingState
import com.app.igrow.ui.diagnose.DiagnoseFragment
import com.app.igrow.ui.diagnose.DiagnoseFragment.Companion.ARG_SEARCH_RESULT_ITEM_KEY
import com.app.igrow.utils.Constants
import com.app.igrow.utils.Constants.COL_COMPOSITION
import com.app.igrow.utils.Constants.COL_COMPOSITION_FR
import com.app.igrow.utils.Constants.COL_CROP
import com.app.igrow.utils.Constants.COL_CROP_FR
import com.app.igrow.utils.Constants.COL_DISTRIBUTOR
import com.app.igrow.utils.Constants.COL_DISTRIBUTOR_FR
import com.app.igrow.utils.Constants.COL_ENEMY
import com.app.igrow.utils.Constants.COL_ENEMY_FR
import com.app.igrow.utils.Constants.COL_PRODUCTS_CATEGORY
import com.app.igrow.utils.Constants.COL_PRODUCTS_CATEGORY_FR
import com.app.igrow.utils.Constants.COL_PRODUCT_NAME
import com.app.igrow.utils.Constants.COL_TYPE_OF_ENEMY
import com.app.igrow.utils.Constants.COL_TYPE_OF_ENEMY_FR
import com.app.igrow.utils.Constants.SHEET_PRODUCTS
import com.app.igrow.utils.Utils.getLocalizeColumnName
import com.app.igrow.utils.Utils.isLocaleFrench
import com.app.igrow.utils.gone
import com.app.igrow.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.concurrent.schedule

@AndroidEntryPoint
class ProductsFragment : BaseFragment<FragmentProductsBinding>() {

    private val viewModel: ProductsViewModel by viewModels()
    private lateinit var listDialog: Dialog
    private lateinit var dialogeLayoutBinding: DialogeLayoutBinding
    private lateinit var dialogListAdapter: DialogListAdapter

    private var productColumnName = ""
    private var dialogList = arrayListOf<String>()
    private var filteredList = arrayListOf<String>()
    private var productFiltersHashMap = HashMap<String, String>()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentProductsBinding
        get() = FragmentProductsBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activateListener()
        activateObserver()

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.toHomePageFromProducts)
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

    }

    override fun onResume() {
        super.onResume()
        setupInitialData()
    }

    private fun setupInitialData() {

        arguments?.let {
            if (it.isEmpty.not() && it.containsKey(PRODUCT_INITIAL_DATA)) {
                val argsItem = it.get(PRODUCT_INITIAL_DATA) as HashMap<*, *>
                argsItem.forEach { item ->
                    productColumnName = item.key.toString()
                    setSelectedValueToView(item.value.toString())
                    populateFiltersObject(item.value.toString() + ":")
                }
            } else if (it.isEmpty.not() && it.containsKey(ARG_SEARCH_RESULT_ITEM_KEY)) {
                val argsItem = it.get(ARG_SEARCH_RESULT_ITEM_KEY) as Diagnostic

                productColumnName = COL_CROP
                var value = if (isLocaleFrench()) argsItem.crop_fr else argsItem.crop
                //setSelectedValueToView(value)
                //populateFiltersObject("$value:")
                viewModel.isColumnValueExist(productColumnName, value, SHEET_PRODUCTS)

//                productColumnName = COL_PLANT_HEALTH_PROBLEM
//                value = if (isLocaleFrench()) argsItem.plant_health_problem_fr else argsItem.plant_health_problem
//                setSelectedValueToView(value)
//                populateFiltersObject("$value:")

                //setSelectedValueToView(value)
                //populateFiltersObject("$value:")
                Timer().schedule(8000) {
                    productColumnName = COL_TYPE_OF_ENEMY
                    value =
                        if (isLocaleFrench()) argsItem.type_of_enemy_fr else argsItem.type_of_enemy
                    viewModel.isColumnValueExist(productColumnName, value, SHEET_PRODUCTS)
                }
            }
        }
    }

    private fun activateListener() {
        try {

            binding.llCrop.setOnClickListener {
                productColumnName = getLocalizeColumnName(COL_CROP)
                viewModel.getProductColumnData(
                    productFiltersHashMap,
                    COL_CROP,
                    Constants.SHEET_PRODUCTS
                )
            }
            binding.llEnemy.setOnClickListener {
                productColumnName = getLocalizeColumnName(COL_ENEMY)
                viewModel.getProductColumnData(
                    productFiltersHashMap,
                    COL_ENEMY,
                    Constants.SHEET_PRODUCTS
                )
            }
            binding.llComposition.setOnClickListener {
                productColumnName = getLocalizeColumnName(COL_COMPOSITION)
                viewModel.getProductColumnData(
                    productFiltersHashMap,
                    COL_COMPOSITION,
                    Constants.SHEET_PRODUCTS
                )
            }
            binding.llEnemyType.setOnClickListener {
                productColumnName = getLocalizeColumnName(COL_TYPE_OF_ENEMY)
                viewModel.getProductColumnData(
                    productFiltersHashMap,
                    COL_TYPE_OF_ENEMY,
                    Constants.SHEET_PRODUCTS
                )
            }
            binding.llProductCategory.setOnClickListener {
                productColumnName = getLocalizeColumnName(COL_PRODUCTS_CATEGORY)
                viewModel.getProductColumnData(
                    productFiltersHashMap,
                    COL_PRODUCTS_CATEGORY,
                    Constants.SHEET_PRODUCTS
                )
            }
            binding.llDistributor.setOnClickListener {
                productColumnName = getLocalizeColumnName(COL_DISTRIBUTOR)
                viewModel.getProductColumnData(
                    productFiltersHashMap,
                    COL_DISTRIBUTOR,
                    Constants.SHEET_PRODUCTS
                )
            }
            binding.btnSearch.setOnClickListener {
                if (binding.etSearch.text.toString().isNotEmpty()) {
                    productFiltersHashMap.clear()
                    productFiltersHashMap[getLocalizeColumnName(COL_PRODUCT_NAME)] =
                        binding.etSearch.text.toString().trim()
                }
                viewModel.searchProduct(productFiltersHashMap)

            }

            binding.etSearch.doAfterTextChanged { editable ->
                if (editable != null && (editable.isEmpty() || editable.isBlank())) {
                    productFiltersHashMap.remove(COL_PRODUCT_NAME)
                }
            }

            binding.btnReset.setOnClickListener {
                resetAllFilters()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun activateObserver() {
        viewModel.uiStateLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is LoadingState -> {
                    binding.pbProduct.visible()
                }
                is UnloadingState -> {
                    binding.pbProduct.gone()

                }
            }
        }
        viewModel.getProductColumnDataLiveData.observe(viewLifecycleOwner) {
            if (it != null && it.size > 0) {
                it.sort()
                if (productColumnName.isNotEmpty()) {
                    addPlaceholderInFilterList(it)
                }
                dialogList = it
                showListDialog(it)
            } else {
                Toast.makeText(requireContext(), "No data found ", Toast.LENGTH_LONG).show()
            }
        }

        viewModel.filtersLiveData.observe(viewLifecycleOwner) { response ->
            if (response.isNotEmpty()) {
                val searchResultData =
                    SearchResult(productFiltersHashMap, response)
                val bundle = bundleOf(DiagnoseFragment.ARG_RESULT_KEY to searchResultData)
                findNavController().navigate(R.id.toProductsSearchResultFragment, bundle)
            } else {
                if (viewModel.showEmptyListMsg())
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.no_data_found),
                        Toast.LENGTH_LONG
                    ).show()
            }
        }
        viewModel.columnDataExistLiveData.observe(viewLifecycleOwner) {
            val data = it?.split("-")
            if (data?.get(2)?.isNotEmpty() == true && data[2] != "null") {
                val value = data[1]
                setSelectedValueToView(value)
                populateFiltersObject("$value:")
            }
        }
    }

    private fun addPlaceholderInFilterList(dataList: ArrayList<String>) {
        when (productColumnName) {
            COL_CROP, COL_CROP_FR -> {
                dataList.add(0, getString(R.string.all_crops))
                return
            }
            COL_TYPE_OF_ENEMY, COL_TYPE_OF_ENEMY_FR -> {
                dataList.add(0, getString(R.string.all_enemy_types))
                return
            }
            COL_ENEMY, COL_ENEMY_FR -> {
                dataList.add(0, getString(R.string.all_enemies))
                return
            }
            COL_COMPOSITION, COL_COMPOSITION_FR -> {
                dataList.add(0, getString(R.string.all_compositions))
                return
            }
            COL_PRODUCTS_CATEGORY, COL_PRODUCTS_CATEGORY_FR -> {
                dataList.add(0, getString(R.string.all_product_categories))
                return
            }
            COL_DISTRIBUTOR, COL_DISTRIBUTOR_FR -> {
                dataList.add(0, getString(R.string.all_distributors))
                return
            }

        }
    }

    private fun showListDialog(dataList: ArrayList<String>) {
        listDialog = Dialog(requireContext())
        listDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        dialogeLayoutBinding = DialogeLayoutBinding.inflate(LayoutInflater.from(context))
        listDialog.setContentView(dialogeLayoutBinding.root)

        listDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogeLayoutBinding.rcListDialog.layoutManager = LinearLayoutManager(requireContext())
        dialogeLayoutBinding.rcListDialog.setHasFixedSize(true)

        setDialogListToAdapter(dataList)

        listDialog.show()


        dialogeLayoutBinding.etListDialogSearch.doAfterTextChanged { editable ->
            if (!editable.isNullOrEmpty() && editable.isNotBlank()) {
                val myResult = searchList(editable.toString())
                setDialogListToAdapter(myResult)
            } else {
                filteredList.clear()
                setDialogListToAdapter(dialogList)
            }
        }
    }

    private fun searchList(searchValue: String): ArrayList<String> {
        try {
            filteredList.clear()
            if (dialogList.size < 0) {
                return arrayListOf()
            }
            filteredList = dialogList.filter { model ->
                model.lowercase(Locale.getDefault())
                    .contains(searchValue.lowercase(Locale.getDefault()))
            } as ArrayList<String>

            if (filteredList.isEmpty()) {
                filteredList.add(getString(R.string.no_record_found))
                return filteredList
            }
            return filteredList
        } catch (ex: Exception) {
            Log.e(DiagnoseFragment.TAG, ex.stackTraceToString())
        }
        return arrayListOf()
    }

    private fun setDialogListToAdapter(dataList: ArrayList<String>) {
        dialogListAdapter = DialogListAdapter { uiValue, position ->
            val selectedValue = if (filteredList.isNotEmpty() && position <= filteredList.size) {
                filteredList[position]
            } else {
                dialogList[position]
            }
            listDialog.dismiss()
            setSelectedValueToView(uiValue)
            populateFiltersObject(selectedValue)
        }

        dialogListAdapter.differ.submitList(dataList)
        dialogeLayoutBinding.rcListDialog.adapter = dialogListAdapter
    }

    private fun setSelectedValueToView(value: String) {
        if (productColumnName.isNotEmpty()) {
            when (productColumnName) {
                COL_CROP, COL_CROP_FR -> {
                    binding.tvCropFilterText.text = ""
                    binding.tvCropFilterText.text = value
                    return
                }
                COL_TYPE_OF_ENEMY, COL_TYPE_OF_ENEMY_FR -> {
                    binding.tvTypeOfEnemyFilterText.text = ""
                    binding.tvTypeOfEnemyFilterText.text = value
                    return
                }
                COL_ENEMY, COL_ENEMY_FR -> {
                    binding.tvEnemyFilterText.text = ""
                    binding.tvEnemyFilterText.text = value
                    return
                }
                COL_COMPOSITION, COL_COMPOSITION_FR -> {
                    binding.tvCompositionFilterText.text = ""
                    binding.tvCompositionFilterText.text = value
                    return
                }
                COL_PRODUCTS_CATEGORY, COL_PRODUCTS_CATEGORY_FR -> {
                    binding.tvProductCategoryFilterText.text = ""
                    binding.tvProductCategoryFilterText.text = value
                    return
                }
                COL_DISTRIBUTOR, COL_DISTRIBUTOR_FR -> {
                    binding.tvDistributorFilterText.text = ""
                    binding.tvDistributorFilterText.text = value
                    return
                }
            }
        }
    }

    private fun populateFiltersObject(value: String) {
        val englishAndFrenchValues = value.split(":")
        if (productColumnName.isNotEmpty() &&
            englishAndFrenchValues[0] != getString(R.string.all_crops) &&
            englishAndFrenchValues[0] != getString(R.string.all_enemy_types) &&
            englishAndFrenchValues[0] != getString(R.string.all_enemies) &&
            englishAndFrenchValues[0] != getString(R.string.all_compositions) &&
            englishAndFrenchValues[0] != getString(R.string.all_product_categories) &&
            englishAndFrenchValues[0] != getString(R.string.all_distributors)
        ) {
            productFiltersHashMap[productColumnName] = englishAndFrenchValues[0]
        } else {
            productFiltersHashMap.remove(productColumnName)
        }
    }

    private fun resetAllFilters() {
        binding.etSearch.text?.clear()
        if (productFiltersHashMap.isNotEmpty()) {
            binding.tvCropFilterText.text = getString(R.string.crop)
            binding.tvTypeOfEnemyFilterText.text = getString(R.string.enemy_type)
            binding.tvEnemyFilterText.text = getString(R.string.enemy)
            binding.tvCompositionFilterText.text = getString(R.string.composition)
            binding.tvProductCategoryFilterText.text = getText(R.string.product_category)
            binding.tvDistributorFilterText.text = getText(R.string.distributor)
            productFiltersHashMap.clear()
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.filtersLiveData.value = arrayListOf()
        viewModel.getProductColumnDataLiveData.value = arrayListOf()
    }

    companion object {
        const val PRODUCT_INITIAL_DATA = "product_initial_data"
    }

}