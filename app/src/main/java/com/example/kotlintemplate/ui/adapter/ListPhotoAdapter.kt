package com.example.kotlintemplate.ui.adapter

import com.example.kotlintemplate.R
import com.example.kotlintemplate.base.BaseAdapter
import com.example.kotlintemplate.common.OnItemClickListener
import com.example.kotlintemplate.network.model.listphoto.ListPhotoModel

class ListPhotoAdapter(onItemClickListener: OnItemClickListener) : BaseAdapter<ListPhotoModel.CustomList>(R.layout.item_photo_list, onItemClickListener) {

}