package com.example.kotlintest.ui.fragment

import com.example.kotlintest.R
import com.example.kotlintest.base.BaseFragment
import com.example.kotlintest.databinding.FragmentFirstBinding
import com.example.kotlintest.extension.setOnSingleClickListener

class FirstFragment : BaseFragment<FragmentFirstBinding>(R.layout.fragment_first) {

    override fun init() {
        binding.btnToSecond.setOnSingleClickListener {
            navController.navigate(R.id.action_to_second_fragment)
        }

        binding.btnToThird.setOnSingleClickListener {
            navController.navigate(R.id.action_to_third_fragment)
        }

        binding.btnToList.setOnSingleClickListener {
            navController.navigate(R.id.action_to_list_fragment)
        }
    }

}