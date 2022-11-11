package com.app.igrow.ui.splash

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.app.igrow.R
import com.app.igrow.base.BaseActivity
import com.app.igrow.databinding.ActivitySplashBinding
import com.app.igrow.ui.dashboard.DashBoardActivity
import com.app.igrow.utils.Constants
import com.app.igrow.utils.Utils
import com.app.igrow.utils.gone
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_splash)
        observer()
    }

    private fun observer() {
        viewModel.isLocalDatBaseEmptyLiveData.observe(this) {
            if (it) {
                if (Utils.isInternetAvailable(this)) {
                    createDatabase()
                } else {
                    showConnectInternetAlert()
                }
            } else {
                navigateToNext()
            }
        }
    }

    private fun navigateToNext() {

        binding.ivLogo.animate().alpha(1F).setDuration(2000)
            .setInterpolator(DecelerateInterpolator())
            .withEndAction(
                Runnable {
                    binding.ivLogo.animate().alpha(0F).setDuration(2000)
                        .setInterpolator(AccelerateInterpolator()).start()

                    binding.pbProduct.gone()
                    startActivity(Intent(this, DashBoardActivity::class.java))
                    finish()
                }).start()
    }

    private fun createDatabase() {
        viewModel.insertDataForGivenTable(Constants.SHEET_DIAGNOSTIC)
        viewModel.insertDataForGivenTable(Constants.SHEET_DISTRIBUTORS)
        viewModel.insertDataForGivenTable(Constants.SHEET_DEALERS)
        viewModel.insertDataForGivenTable(Constants.SHEET_PRODUCTS)
        navigateToNext()
    }

    private fun showConnectInternetAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.no_internet_error))
        builder.setMessage(getString(R.string.no_internet_error_message))
        builder.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_error))
        builder.setPositiveButton(getString(R.string.ok), null)
        //performing positive action
        val alertDialog = builder.create()
        alertDialog.setCancelable(false)

        alertDialog.setOnShowListener {
            val positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setOnClickListener {
                alertDialog.dismiss()
            }
        }
        alertDialog.show()
    }
}