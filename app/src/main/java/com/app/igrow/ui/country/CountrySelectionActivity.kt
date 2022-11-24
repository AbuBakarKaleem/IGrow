package com.app.igrow.ui.country

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import com.app.igrow.R
import com.app.igrow.base.BaseActivity
import com.app.igrow.databinding.ActivityCountrySelectionBinding
import com.app.igrow.ui.dashboard.DashBoardActivity


class CountrySelectionActivity : BaseActivity() {

    private lateinit var binding: ActivityCountrySelectionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountrySelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        activateListener()
    }

    private fun init() {
        binding.info.text = getString(R.string.edit_diagnostic) + "-" +
                getString(R.string.text_solution) + "-" +
                getString(R.string.shop)
    }

    private fun activateListener() {
        binding.spinnerCountry.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                if (binding.spinnerCountry.selectedItem.equals(getString(R.string.text_select_country))
                        .not()
                ) {
                    getPreferenceManager()?.setCountry(binding.spinnerCountry.selectedItem.toString())
                    startActivity(Intent(context,DashBoardActivity::class.java))
                    finish()
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
            }
        }
    }
}