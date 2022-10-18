package com.app.igrow.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    }

    private fun activateListeners() {
        binding.diagnoseBtn.setOnClickListener {
            findNavController().navigate(R.id.homeToDiagnoseFragment)
        }
        binding.productBtn.setOnClickListener {
            findNavController().navigate(R.id.homeToProductsFragment)
        }
    }

}