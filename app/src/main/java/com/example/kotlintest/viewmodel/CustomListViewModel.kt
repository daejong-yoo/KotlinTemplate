package com.example.kotlintest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CustomListViewModel : ViewModel() {
    private val _gameTitle: MutableLiveData<String> = MutableLiveData()
    val gameTitle: LiveData<String> = _gameTitle

    private val _imageUrl: MutableLiveData<String> = MutableLiveData()
    val imageUrl: LiveData<String> = _imageUrl

    private val _rating: MutableLiveData<String> = MutableLiveData()
    val rating: LiveData<String> = _rating

    fun updateGameTitle(gameTitle: String) {
        _gameTitle.value = gameTitle
    }

    fun updateImgUrl(imgUrl: String) {
        _imageUrl.value = imgUrl
    }
}