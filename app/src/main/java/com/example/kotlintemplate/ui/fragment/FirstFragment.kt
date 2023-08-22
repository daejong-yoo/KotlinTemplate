package com.example.kotlintemplate.ui.fragment

import com.example.kotlintemplate.R
import com.example.kotlintemplate.base.BaseFragment
import com.example.kotlintemplate.databinding.FragmentFirstBinding
import com.example.kotlintemplate.extension.setOnSingleClickListener

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