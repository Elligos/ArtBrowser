package com.example.deviantartviewer.ui.base

import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.deviantartviewer.ui.browse.images.ImageDiffUtils

abstract class BaseAdapter<T : Any, VH : BaseItemViewHolder<T, BaseViewModel>>(
        val viewModel: BaseViewModel,
        val diffUtil: DiffUtil.ItemCallback<T>
) : RecyclerView.Adapter<VH>() {

private var recyclerView: RecyclerView? = null


    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        this.recyclerView = null
    }

    val differ = AsyncListDiffer(this, diffUtil)

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = differ.currentList[position]

        holder.itemView.apply{
            setOnClickListener {
                onItemClickListener?.let { it(item) }
            }
        }
        holder.bind(item)

    }

    fun updateData(data: List<T>){
        differ.submitList(null)
        notifyDataSetChanged()
        differ.submitList(data)
    }

    fun updateData(){
        notifyDataSetChanged()
    }

    private var onItemClickListener: ((T) -> Unit)? = null

    fun setOnItemClickListener(listener: (T) -> Unit) {
        onItemClickListener = listener
    }
}