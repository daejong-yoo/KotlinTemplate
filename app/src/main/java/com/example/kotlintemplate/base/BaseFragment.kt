package com.example.kotlintemplate.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.kotlintemplate.util.GLog

abstract class BaseFragment<T : ViewDataBinding>(@LayoutRes val layoutId: Int) : Fragment() {
    protected lateinit var binding: T
    protected var mActivity: BaseActivity<*>? = null
    protected lateinit var navController: NavController

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
        navController = NavHostFragment.findNavController(this)

        init()
    }

    abstract fun init()

}