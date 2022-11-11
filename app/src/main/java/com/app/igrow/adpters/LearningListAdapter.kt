package com.app.igrow.adpters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.igrow.databinding.LearningsListItemBinding

class LearningListAdapter(val onItemClicked: (Item: String) -> Unit,
                          val onShareClicked: (Item: String) -> Unit) :
    RecyclerView.Adapter<LearningListAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(
        private val itemBinding: LearningsListItemBinding
    ) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(model: String) {
            itemBinding.apply {
                this.tvSearchResultName.text = model    
                this.tvSearchResultName.setOnClickListener {
                    onItemClicked(model)
                }
                this.ivShareItem.setOnClickListener {
                    onShareClicked(model)
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
        val binding: LearningsListItemBinding =
            LearningsListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount() = differ.currentList.size
}