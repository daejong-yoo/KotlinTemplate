package com.example.kotlintest.ui.adapter

import com.example.kotlintest.R
import com.example.kotlintest.base.BaseAdapter
import com.example.kotlintest.common.OnItemClickListener
import com.example.kotlintest.network.model.listphoto.ListPhotoModel

class ListPhotoAdapter(onItemClickListener: OnItemClickListener) : BaseAdapter<ListPhotoModel.CustomList>(R.layout.item_photo_list, onItemClickListener) {

}