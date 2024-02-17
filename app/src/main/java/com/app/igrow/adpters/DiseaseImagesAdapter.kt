package com.app.igrow.adpters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.igrow.data.model.sheets.Diagnostic
import com.app.igrow.databinding.DiagnoseDiseaseListItemBinding

class DiseaseImagesAdapter(private val dataset: List<Diagnostic>) :
    RecyclerView.Adapter<DiseaseImagesAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(
        private val itemBinding: DiagnoseDiseaseListItemBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(model: Diagnostic) {
            itemBinding.apply {
//                this.ivImageDisease.setImageURI(model.image_sample)
            }
        }
    }

    private val differCallBack = object : DiffUtil.ItemCallback<Diagnostic>() {

        override fun areItemsTheSame(oldItem: Diagnostic, newItem: Diagnostic): Boolean {
            return oldItem.image_sample == newItem.image_sample
        }

        override fun areContentsTheSame(oldItem: Diagnostic, newItem: Diagnostic): Boolean {
            return oldItem.image_sample == newItem.image_sample
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = DiagnoseDiseaseListItemBinding.inflate(
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
