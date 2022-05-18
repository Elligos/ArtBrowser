package com.example.deviantartviewer.ui.base

import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T : Any, VH : BaseItemViewHolder<T, BaseViewModel>>(
        private val dataList: ArrayList<T>, val viewModel: BaseViewModel
) : RecyclerView.Adapter<VH>() {

private var recyclerView: RecyclerView? = null


    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        this.recyclerView = null
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(dataList[position])
    }

    fun appendData(data: List<T>) {
        val oldCount = itemCount
        this.dataList.addAll(data)
        val currentCount = itemCount
        if (oldCount == 0 && currentCount > 0)
            notifyDataSetChanged()
        else if (oldCount in 1 until currentCount)
            notifyItemRangeChanged(oldCount - 1, currentCount - oldCount)
    }

    fun updateData(data: List<T>){
        this.dataList.clear()
        this.dataList.addAll(data)
        notifyDataSetChanged()
    }

    fun updateData(){
        notifyDataSetChanged()
    }
}