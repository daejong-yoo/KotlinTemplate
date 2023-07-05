package com.example.kotlintest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlintest.network.model.listphoto.ListPhotoModel

class ListPhotoViewModel : ViewModel() {
    private val _listPhotoData = MutableLiveData<ListPhotoModel.Data>()
    val listPhotoData: LiveData<ListPhotoModel.Data> = _listPhotoData

    fun setListPhotoData(listPhotoData: ListPhotoModel.Data) {
        _listPhotoData.value = listPhotoData
    }

}