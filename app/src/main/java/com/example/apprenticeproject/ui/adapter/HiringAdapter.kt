package com.example.apprenticeproject.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.apprenticeproject.databinding.HeaderItemBinding
import com.example.apprenticeproject.databinding.HiringItemBinding
import com.example.apprenticeproject.network.responses.HiringResponse

class HiringAdapter(
    private val hiring: ArrayList<Any>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return if (viewType == 0) {
            val binding = HeaderItemBinding.inflate(LayoutInflater.from(parent.context))
            HeaderViewHolder(binding)
        } else {
            val binding = HiringItemBinding.inflate(LayoutInflater.from(parent.context))
            HiringViewHolder(binding)
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val item = hiring[position]
        when (holder) {
            is HeaderViewHolder -> holder.bind(item as HeaderItem)
            is HiringViewHolder -> holder.bind(item as HiringResponse)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(hiring[position] is HeaderItem) {
            0
        } else {
            1
        }
    }

    override fun getItemCount(): Int = hiring.size

    class HiringViewHolder(
        private val binding: HiringItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(hiringResponse: HiringResponse) {
            binding.title.text = hiringResponse.name
        }
    }

    class HeaderViewHolder(
        private val binding: HeaderItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(header: HeaderItem) {
            binding.header.text = header.listId.toString()
        }
    }
}