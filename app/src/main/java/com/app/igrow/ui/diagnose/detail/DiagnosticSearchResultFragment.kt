package com.app.igrow.ui.diagnose.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.app.igrow.R
import com.app.igrow.databinding.FragmentSearchResultBinding
import com.app.igrow.ui.diagnose.DiagnoseFragment
import com.google.android.material.chip.Chip

class DiagnosticSearchResultFragment : Fragment() {

    private lateinit var fragmentSearchResultBinding: FragmentSearchResultBinding
    private val binding get() = fragmentSearchResultBinding
    private lateinit var viewModel: DiagnosticSearchResultViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragmentSearchResultBinding =
            FragmentSearchResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val filterArgs = it.get(DiagnoseFragment.ARG_FILTERS_KEY)
            println("=-=>> $filterArgs")
        }

    }

    private fun addChipToGroup(value: String) {
        val chip = Chip(context)
        chip.text = value
        chip.chipIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_cancel)
        chip.isChipIconVisible = false
        chip.isCloseIconVisible = true

        // necessary to get single selection working
        chip.isClickable = true
        chip.isCheckable = false
        binding.chipGroup.addView(chip as View)
        chip.setOnCloseIconClickListener {
            binding.chipGroup.removeView(chip as View)
        }
    }

}