package com.app.igrow.adpters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.igrow.data.model.sheets.Video
import com.app.igrow.databinding.LearningsListItemBinding
import com.app.igrow.utils.Utils

class LearningListAdapter(val onItemClicked: (videoLink: String) -> Unit,
                          val onShareClicked: (videoLink: String) -> Unit) :
    RecyclerView.Adapter<LearningListAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(
        private val itemBinding: LearningsListItemBinding
    ) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(model: Video) {
            itemBinding.apply {
                val title = if (Utils.isLocaleFrench()) model.title_fr else model.title
                this.tvSearchResultName.text = title

                val link = if (Utils.isLocaleFrench()) model.link_fr else model.link
                this.tvSearchResultName.setOnClickListener {
                    onItemClicked(link)
                }
                this.ivShareItem.setOnClickListener {
                    onShareClicked(link)
                }
            }
        }
    }

    private val differCallBack = object : DiffUtil.ItemCallback<Video>() {

        override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean {
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