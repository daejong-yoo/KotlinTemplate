package com.example.kotlintest.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.kotlintest.util.GLog

abstract class BaseFragment<T : ViewDataBinding>(@LayoutRes val layoutId: Int) : Fragment() {
    lateinit var binding: T
    protected var mActivity: BaseActivity<*>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        try {
            binding = DataBindingUtil.inflate(inflater, layoutId, container, false)

            requireActivity().let {
                mActivity = if (it is BaseActivity<*>) it else null
            }
        } catch (e: Exception) {
            GLog.e("BaseFragment exception : ${e.message}")
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner

        init()
    }

    abstract fun init()

}