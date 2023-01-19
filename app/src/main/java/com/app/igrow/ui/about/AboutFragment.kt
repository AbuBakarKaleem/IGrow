package com.app.igrow.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.app.igrow.R
import com.app.igrow.base.BaseFragment
import com.app.igrow.databinding.FragmentAboutBinding
import com.app.igrow.databinding.FragmentDistributorDetailBinding

class AboutFragment : BaseFragment<FragmentAboutBinding>()  {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAboutBinding
        get() = FragmentAboutBinding::inflate


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.aboutFragToMoreFragment)
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

    }
}