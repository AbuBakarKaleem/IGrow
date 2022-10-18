package com.app.igrow.adpters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.igrow.data.model.detail.SearchResult
import com.app.igrow.data.model.sheets.Diagnostic
import com.app.igrow.databinding.SearchResultListItemBinding

class DiagnosticSearchResultAdapter (val onImageClicked: (Item: Diagnostic) -> Unit):
    RecyclerView.Adapter<DiagnosticSearchResultAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(
        private val context: Context,
        private val itemBinding: SearchResultListItemBinding
    ) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(model: Diagnostic) {
            itemBinding.apply {
                this.tvSearchResultName.text= model.crop
                this.tvSearchResultDetail.text= model.causal_agent
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
        return ItemViewHolder(parent.context, binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount()= differ.currentList.size
}