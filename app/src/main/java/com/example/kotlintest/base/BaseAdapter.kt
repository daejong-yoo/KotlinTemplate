package com.example.kotlintest.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlintest.BR
import com.example.kotlintest.common.OnItemClickListener
import com.example.kotlintest.util.GLog

abstract class BaseAdapter<T>(@LayoutRes val layoutId: Int, listener: OnItemClickListener) : RecyclerView.Adapter<BaseAdapter<T>.ViewHolder>() {
    private val _itemList: MutableList<T> = mutableListOf()
    private var mListener: OnItemClickListener

    init {
        mListener = listener
    }

    inner class ViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: T) {
            try {
                binding.setVariable(BR.position, adapterPosition)
                binding.setVariable(BR.item, item)
                binding.setVariable(BR.listener, mListener)
            } catch (e: Exception) {
                GLog.e("BaseAdapter ViewHolder bind exception : ${e.message}")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ViewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), layoutId, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return _itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(_itemList[position])
    }

    fun replaceList(itemList: List<T>?) {
        itemList?.let {
            _itemList.clear()
            _itemList.addAll(it)
            notifyDataSetChanged()
        }
    }

    fun addAll(itemList: List<T>?) {
        itemList?.let {
            _itemList.addAll(it)
            notifyDataSetChanged()
        }
    }

    fun add(item: T?) {
        item?.let {
            val position = _itemList.size
            _itemList.add(position, it)
            notifyItemInserted(position)
        }
    }

    fun remove(position: Int) {
        if (position < 0)
            return

        _itemList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun remove(item: T?) {
        item?.let {
            val position = _itemList.indexOf(it)

            if (position < 0)
                return

            if (_itemList.contains(it)) _itemList.remove(it)

            notifyItemRemoved(position)
        }
    }

    fun getPosition(item: T?): Int {
        item?.let {
            for (i in 0 until _itemList.size) {
                if (it == _itemList[i])
                    return i
            }
        }

        return -1
    }

    fun clear() {
        _itemList.clear()
    }
}