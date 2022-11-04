package com.app.igrow.adpters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.igrow.data.model.sheets.Dealers
import com.app.igrow.databinding.ProductSearchResultListItemBinding

class DealersListAdapter(val onItemClicked: (Item: Dealers) -> Unit) :
    RecyclerView.Adapter<DealersListAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(
        private val itemBinding: ProductSearchResultListItemBinding
    ) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(model: Dealers) {
            itemBinding.apply {
                this.tvSearchResultName.text = model.dealer_name
                this.tvSearchResultDetail.text = model.city_town
                this.cvSearchResult.setOnClickListener {
                    onItemClicked(model)
                }
            }
        }
    }

    private val differCallBack = object : DiffUtil.ItemCallback<Dealers>() {

        override fun areItemsTheSame(oldItem: Dealers, newItem: Dealers): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Dealers, newItem: Dealers): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding: ProductSearchResultListItemBinding =
            ProductSearchResultListItemBinding.inflate(
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