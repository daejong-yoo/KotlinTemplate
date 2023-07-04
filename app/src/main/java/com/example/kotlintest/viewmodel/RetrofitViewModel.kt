package com.example.kotlintest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlintest.custom.ListLiveData
import com.example.kotlintest.network.model.CustomList


class RetrofitViewModel: ViewModel() {

    val mainTitle: MutableLiveData<String> = MutableLiveData()

    val list: ListLiveData<CustomList> = ListLiveData()

    fun updateMainTitle(title: String?) {
        title?.let {
            this.mainTitle.value =  it
        }
    }

    fun updateListItem(list: Collection<CustomList>) {
        this.list.addAll(list)
    }
}