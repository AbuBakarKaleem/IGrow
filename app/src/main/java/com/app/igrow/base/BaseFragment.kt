package com.app.igrow.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.app.igrow.utils.LocaleHelper

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    private var _bi: VB? = null
    protected val binding: VB get() = _bi!!
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bi = bindingInflater(inflater, container, false)
        return _bi!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bi = null
    }
}
