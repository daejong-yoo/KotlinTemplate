package com.example.kotlintest.ui.fragment

import com.example.kotlintest.R
import com.example.kotlintest.base.BaseFragment
import com.example.kotlintest.databinding.FragmentFirstBinding

class FirstFragment : BaseFragment<FragmentFirstBinding>(R.layout.fragment_first) {

    override fun init() {
        binding.btnToSecond.setOnClickListener {
            navController.navigate(R.id.action_to_second_fragment)
        }

        binding.btnToThird.setOnClickListener {
            navController.navigate(R.id.action_to_third_fragment)
        }
    }

}