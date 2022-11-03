package com.app.igrow.adpters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.igrow.data.model.sheets.Diagnostic
import com.app.igrow.databinding.SearchResultListItemBinding
import com.app.igrow.utils.Utils

class DiagnosticSearchResultAdapter (val onImageClicked: (Item: Diagnostic) -> Unit):
    RecyclerView.Adapter<DiagnosticSearchResultAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(
        private val itemBinding: SearchResultListItemBinding
    ) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(model: Diagnostic) {
            itemBinding.apply {
                var value = if(Utils.isLocaleFrench()) model.crop_fr  else model.crop
                this.tvSearchResultName.text = value

                value = if(Utils.isLocaleFrench()) model.causal_agent_fr  else model.causal_agent
                this.tvSearchResultDetail.text = value
                this.cvSearchResult.setOnClickListener {
                    onImageClicked(model)
                }
            }
        }
    }
    private val differCallBack = object : DiffUtil.ItemCallback<Diagnostic>() {

        override fun areItemsTheSame(oldItem: Diagnostic, newItem: Diagnostic): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Diagnostic, newItem: Diagnostic): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = SearchResultListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount()= differ.currentList.size
}