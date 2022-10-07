package com.app.igrow.adpters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.igrow.databinding.DialogListViewLayoutBinding

class DialogListAdapter(val onImageClicked: (Item: String) -> Unit) :
    RecyclerView.Adapter<DialogListAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(
        private val context: Context,
        private val itemBinding: DialogListViewLayoutBinding
    ) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(model: String) {
            itemBinding.apply {
                this.tvListLabel.text = model
                this.tvListLabel.setOnClickListener {
                    onImageClicked(model)
                }
            }
        }
    }

    private val differCallBack = object : DiffUtil.ItemCallback<String>() {

        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallBack)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = DialogListViewLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder(parent.context, binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount() = differ.currentList.size

}