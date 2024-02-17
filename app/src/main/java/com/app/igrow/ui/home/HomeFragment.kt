package com.app.igrow.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.app.igrow.R
import com.app.igrow.databinding.FragmentHomeBinding
import com.app.igrow.utils.Utils.getSystemLanguage

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activateListeners()
        getSystemLanguage()

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    showExitAlertDialog()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun activateListeners() {
        binding.diagnoseBtn.setOnClickListener {
            findNavController().navigate(R.id.homeToDiagnoseFragment)
        }
        binding.productBtn.setOnClickListener {
            findNavController().navigate(R.id.homeToProductsFragment)
        }
        binding.shopBtn.setOnClickListener {
            findNavController().navigate(R.id.homeToDealerFragment)
        }

    }

    private fun showExitAlertDialog(){

        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(getString(R.string.exit_dialog_header))
        builder.setMessage(getString(R.string.exit_dialog_message))
        builder.setIcon(ContextCompat.getDrawable(requireActivity(), R.drawable.ic_error))
        builder.setPositiveButton(getString(R.string.exit_dialog_button_text), null)
        builder.setNegativeButton(getString(R.string.cancel), null)
        //performing positive action
        val alertDialog = builder.create()
        alertDialog.setCancelable(true)

        alertDialog.setOnShowListener {
            val positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setOnClickListener {
                alertDialog.dismiss()
                requireActivity().finish()
            }
            val negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
            negativeButton.setOnClickListener {
                alertDialog.dismiss()
            }
        }
        alertDialog.show()
    }

}