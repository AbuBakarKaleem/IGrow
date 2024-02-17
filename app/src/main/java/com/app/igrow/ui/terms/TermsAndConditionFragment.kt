package com.app.igrow.ui.terms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.app.igrow.R
import com.app.igrow.base.BaseFragment
import com.app.igrow.databinding.FragmentTermsAndConditionBinding

class TermsAndConditionFragment : BaseFragment<FragmentTermsAndConditionBinding>()  {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentTermsAndConditionBinding
        get() = FragmentTermsAndConditionBinding::inflate


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.termsFragToMoreFragment)
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

    }
}