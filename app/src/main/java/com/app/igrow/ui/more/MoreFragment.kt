package com.app.igrow.ui.more

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.app.igrow.R
import com.app.igrow.base.BaseFragment
import com.app.igrow.databinding.FragmentMoreBinding
import com.app.igrow.utils.PreferenceManager
import com.app.igrow.utils.Utils
import dagger.hilt.android.AndroidEntryPoint


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
                            showWarningDialog("en")
                        }
                        getString(R.string.language_french) -> {
                            showWarningDialog("fr")
                        }
                    }
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // do nothing
            }
        }

        binding.btnLearningMaterial.setOnClickListener {
            if (Utils.isInternetAvailable(requireContext())) {
                findNavController().navigate(R.id.toLearningsFragment)
            } else {
                Toast.makeText(
                    requireContext(),
                    getText(R.string.no_internet_error),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        binding.btnAbout.setOnClickListener {
             findNavController().navigate(R.id.toAboutFragment)
        }

        binding.btnHelp.setOnClickListener {
            findNavController().navigate(R.id.toHelpFragment)
        }

        binding.btnTermsAndCondition.setOnClickListener {
            findNavController().navigate(R.id.toTermsAndConditionFragment)
        }

        binding.btnInviteAFriend.setOnClickListener {
            val webURL = "http://www.agricadvisors.com/"
            val i = Intent(Intent.ACTION_SEND)
            i.type = "text/plain"
            i.putExtra(Intent.EXTRA_SUBJECT,webURL )
            startActivity(Intent.createChooser(i, "Invite a friend"))
        }

    }

    private fun restartApp(context: Context) {
        Handler().postDelayed({
            val packageManager = context.packageManager
            val intent = packageManager.getLaunchIntentForPackage(context.packageName)
            val componentName = intent!!.component
            val mainIntent = Intent.makeRestartActivityTask(componentName)
            context.startActivity(mainIntent)
            Runtime.getRuntime().exit(0)
        }, 1000)
    }

    fun showWarningDialog(selectedLanguage: String) {
        val dialogClickListener =
            DialogInterface.OnClickListener { dialog, buttonType ->
                when (buttonType) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        myPref.setLanguage(selectedLanguage)
                        restartApp(requireContext())
                    }

                    DialogInterface.BUTTON_NEGATIVE -> {
                        binding.spinnerLanguage.setSelection(0)
                        dialog.dismiss()
                    }
                }
            }
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())

        builder.setTitle(getString(R.string.language_change_warning_message))
        builder.setMessage(getString(R.string.language_change_continue_message))
            .setPositiveButton(getString(R.string.yes), dialogClickListener)
            .setNegativeButton(getString(R.string.no), dialogClickListener)
            .show()
    }

}