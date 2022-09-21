package com.app.igrow.ui.diagnose

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.igrow.R
import com.app.igrow.databinding.FragmentDiagnoseBinding
import com.app.igrow.databinding.FragmentHomeBinding

class DiagnoseFragment : Fragment() {

    private var _binding: FragmentDiagnoseBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: DiagnoseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDiagnoseBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

}