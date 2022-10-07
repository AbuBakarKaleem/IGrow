package com.app.igrow.ui.diagnose.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.igrow.databinding.FragmentSearchResultBinding

class DiagnosticSearchResultFragment : Fragment() {

    private lateinit var fragmentSearchResultBinding: FragmentSearchResultBinding
    private val binding get() = fragmentSearchResultBinding
    private lateinit var viewModel: DiagnosticSearchResultViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentSearchResultBinding =
            FragmentSearchResultBinding.inflate(inflater, container, false)
        return binding.root
    }

}