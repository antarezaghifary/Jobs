package com.reza.jobs.ui.screen.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.reza.jobs.R
import com.reza.jobs.data.model.PositionModel
import com.reza.jobs.databinding.LayoutPositionBinding
import com.reza.jobs.util.loadImage

class ItemPositionAdapter(
    private val listener: (PositionModel.Response.Data) -> Unit
) : ListAdapter<PositionModel.Response.Data, ItemPositionAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder private constructor(
        private val binding: LayoutPositionBinding,
        private val listener: (PositionModel.Response.Data) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PositionModel.Response.Data) {
//            binding.item = item
            if (item.company_logo != null) {
                loadImage(binding.ivCompany, item.company_logo)
            }
            binding.tvPosition.text = item.title
            binding.tvCompany.text = item.company
            binding.tvWork.text = item.location
            itemView.setOnClickListener {
                listener(item)
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(
                parent: ViewGroup,
                listener: (PositionModel.Response.Data) -> Unit
            ): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutPositionBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding, listener)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class DiffCallback : DiffUtil.ItemCallback<PositionModel.Response.Data>() {
        override fun areItemsTheSame(
            oldItem: PositionModel.Response.Data,
            newItem: PositionModel.Response.Data
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: PositionModel.Response.Data,
            newItem: PositionModel.Response.Data
        ): Boolean {
            return oldItem == newItem
        }
    }
}