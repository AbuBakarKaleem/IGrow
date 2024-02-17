package com.app.igrow.ui.splash

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.app.igrow.R
import com.app.igrow.base.BaseActivity
import com.app.igrow.databinding.ActivitySplashBinding
import com.app.igrow.ui.country.CountrySelectionActivity
import com.app.igrow.ui.dashboard.DashBoardActivity
import com.app.igrow.utils.Constants
import com.app.igrow.utils.Utils
import com.app.igrow.utils.gone
import com.app.igrow.worker.NotificationWorker
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.UpdateAvailability
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit
import kotlin.system.exitProcess

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity() {
    private lateinit var binding: ActivitySplashBinding
    private val viewModel: SplashViewModel by viewModels()
    private var permissionGranted = false
    private var permissionRetryAttemptCount = 0
    private lateinit var appUpdateManager:AppUpdateManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_splash)
        appUpdateManager = AppUpdateManagerFactory.create(applicationContext)
        observer()
        checkForNotificationPermission()
        scheduleNotificationWorker()

    }

    private fun scheduleNotificationWorker() {
        if (getPreferenceManager()?.isNotificationScheduled() != true) {
            val notificationWorkRequest = OneTimeWorkRequest.Builder(NotificationWorker::class.java)
                .setInitialDelay(1, TimeUnit.MINUTES)
                .build()
            WorkManager.getInstance(applicationContext)
                .enqueue(notificationWorkRequest)
            getPreferenceManager()?.notificationIsScheduled(true)
        }
    }

    private fun checkForNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), PERMISSION_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)
                ) {
                    navigateToNext()
                } else {
                    if (permissionRetryAttemptCount < 1) {
                        permissionRetryAttemptCount++
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), PERMISSION_REQUEST_CODE)
                        }
                    } else {
                        permissionGranted = false
                        permissionRetryAttemptCount = 0
                        if (NotificationManagerCompat.from(applicationContext)
                                .areNotificationsEnabled()
                                .not()
                        ) {
                            Toast.makeText(
                                this,
                                "Permission Denied.You will not receive Notifications",
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }
                        navigateToNext()
                    }
                }
            }
        }
    }

    private fun observer() {
        viewModel.isLocalDatBaseEmptyLiveData.observe(this) {
            if (it) {
                if (Utils.isInternetAvailable(this)) {
                    createDatabase()
                    if (permissionGranted)
                        navigateToNext()
                } else {
                    showConnectInternetAlert()
                }
            } else {
                if (permissionGranted)
                    navigateToNext()
            }
        }
    }

    private fun navigateToNext() {
        binding.ivLogo.animate()
            .alpha(1F)
            .setDuration(2000)
            .setInterpolator(DecelerateInterpolator())
            .withEndAction {
                binding.ivLogo.animate()
                    .alpha(0F)
                    .setDuration(2000)
                    .setInterpolator(AccelerateInterpolator())
                    .start()
                binding.pbProduct.gone()

                appUpdateManager.appUpdateInfo.addOnSuccessListener {info ->
                    if (info.updateAvailability() == UpdateAvailability.UPDATE_NOT_AVAILABLE){
                        if (getPreferenceManager()?.getCountry()?.isEmpty() == true) {
                            startActivity(Intent(this, CountrySelectionActivity::class.java))
                        } else {
                            startActivity(Intent(this, DashBoardActivity::class.java))
                        }
                        finish()
                    } else if(info.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                        showAppUpdateDialog()
                    } else {
                        startActivity(Intent(this, DashBoardActivity::class.java))
                    }
                }
            }
            .start()
    }

    private fun createDatabase() {
        viewModel.insertDataForGivenTable(Constants.SHEET_DIAGNOSTIC)
        viewModel.insertDataForGivenTable(Constants.SHEET_DISTRIBUTORS)
        viewModel.insertDataForGivenTable(Constants.SHEET_DEALERS)
        viewModel.insertDataForGivenTable(Constants.SHEET_PRODUCTS)
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

    private fun showAppUpdateDialog(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("App Update")
        builder.setMessage("Please update your app to the latest version!")
        builder.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_google_play))
        builder.setPositiveButton(getString(R.string.update), null)
        //performing positive action
        val alertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.setOnShowListener {
            val positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setOnClickListener {
                try {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(ownGooglePlayLink)))
                } catch (exception: ActivityNotFoundException) {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(ownWebLink)))
                }
                alertDialog.dismiss()
                exitProcess(-1)
            }
        }
        runCatching {
            alertDialog.show()
        }
    }

    override fun onResume() {
        super.onResume()
        appUpdateManager.appUpdateInfo.addOnSuccessListener {info ->
            if (info.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                showAppUpdateDialog()
            }
        }
    }

    companion object {
        const val PERMISSION_REQUEST_CODE = 3003
        const val ownGooglePlayLink="market://details?id=com.app.igrow";
        const val ownWebLink="https://play.google.com/store/apps/details?id=com.app.igrow";
    }

}