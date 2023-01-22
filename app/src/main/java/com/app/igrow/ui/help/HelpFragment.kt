package com.app.igrow.ui.help

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.igrow.base.BaseFragment
import com.app.igrow.databinding.FragmentHelpBinding

class HelpFragment : BaseFragment<FragmentHelpBinding>()  {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHelpBinding
        get() = FragmentHelpBinding::inflate


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}