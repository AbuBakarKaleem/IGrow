package com.app.igrow.ui.more

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import com.app.igrow.R
import com.app.igrow.base.BaseFragment
import com.app.igrow.databinding.FragmentMoreBinding
import com.app.igrow.ui.login.LoginActivity
import com.app.igrow.utils.PreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlin.system.exitProcess


@AndroidEntryPoint
class MoreFragment : BaseFragment<FragmentMoreBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMoreBinding
        get() = FragmentMoreBinding::inflate

    private lateinit var viewModel: MoreViewModel
    private lateinit var myPref: PreferenceManager
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        activateListener()
    }

    private fun initView() {
        myPref = PreferenceManager(requireContext())
    }

    private fun activateListener() {
        binding.spinnerLanguage.onItemSelectedListener = object :
            OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                if (position != 0) {
                    when (binding.spinnerLanguage.selectedItem) {
                        getString(R.string.language_english) -> {
                            myPref.setLanguage("en")
                            restartApp(requireContext())
                        }
                        getString(R.string.language_french) -> {
                            myPref.setLanguage("fr")
                            restartApp(requireContext())
                        }
                    }
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // do nothing
            }
        }
    }
    fun restartApp(context: Context) {
        Handler().postDelayed({
            val packageManager = context.packageManager
            val intent = packageManager.getLaunchIntentForPackage(context.packageName)
            val componentName = intent!!.component
            val mainIntent = Intent.makeRestartActivityTask(componentName)
            context.startActivity(mainIntent)
            Runtime.getRuntime().exit(0)
        }, 1000)
    }

}