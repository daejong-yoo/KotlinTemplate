package com.example.kotlintemplate.ui.fragment

import com.example.kotlintemplate.R
import com.example.kotlintemplate.base.BaseFragment
import com.example.kotlintemplate.common.Constant
import com.example.kotlintemplate.common.OnItemClickListener
import com.example.kotlintemplate.databinding.FragmentListBinding
import com.example.kotlintemplate.network.model.listphoto.ListPhotoModel
import com.example.kotlintemplate.network.retrofit.ApiManager
import com.example.kotlintemplate.ui.adapter.ListPhotoAdapter
import com.example.kotlintemplate.util.GLog
import com.example.kotlintemplate.viewmodel.ListPhotoViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListFragment : BaseFragment<FragmentListBinding>(R.layout.fragment_list) {
    private val listPhotoViewModel: ListPhotoViewModel = ListPhotoViewModel()

    private val onItemClickListener = object : OnItemClickListener {
        override fun onItemClick(position: Int, item: Any) {
            GLog.d("position : $position")

            if (item is ListPhotoModel.CustomList) {
                GLog.d("item id : " + item.id)
                GLog.d("item author : " + item.author)
                GLog.d("item url : " + item.url)
                GLog.d("item downloadUrl : " + item.downloadUrl)
            }
        }
    }

    override fun init() {
        val adapter = ListPhotoAdapter(onItemClickListener)
        binding.recyclerView.adapter = adapter

        listPhotoViewModel.listPhotoData.observe(viewLifecycleOwner) {
            adapter.replaceList(it.customList)
        }
        requestListPhoto()
    }

    private fun requestListPhoto() {
        ApiManager.requestListPhoto().enqueue(object : Callback<ListPhotoModel> {
            override fun onResponse(call: Call<ListPhotoModel>, response: Response<ListPhotoModel>) {
                if (response.isSuccessful) {
                    val model: ListPhotoModel? = response.body()
                    model?.let {
                        val code = model.code
                        val errMsg = model.errMsg

                        when (code) {
                            Constant.CODE_SUCCESS -> {
                                model.data?.let {
                                    updateListPhotoViewModel(it)
                                } ?: run {
                                    GLog.e("listPhoto data is null")
                                }
                            }
                            else -> {
                                GLog.e("listPhoto code error : $errMsg")
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ListPhotoModel>, t: Throwable) {

            }
        })
    }

    private fun updateListPhotoViewModel(data: ListPhotoModel.Data) {
        listPhotoViewModel.setListPhotoData(data)
    }
}