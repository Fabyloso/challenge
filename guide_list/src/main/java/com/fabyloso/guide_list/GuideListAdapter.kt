package com.fabyloso.guide_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fabyloso.guide_list.data.local.Event
import com.fabyloso.guide_list.databinding.GuideItemRowBinding
import com.squareup.picasso.Picasso

class GuideListAdapter(private val clickListener: (item: Event) -> Unit = {}) :
    RecyclerView.Adapter<GuideListAdapter.GuideListViewHolder>() {
    var items = emptyList<Event>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuideListViewHolder {
        return GuideListViewHolder(
            GuideItemRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: GuideListViewHolder, position: Int) {
        holder.bind(items[position], clickListener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class GuideListViewHolder(private val binding: GuideItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(event: Event, clickListener: (item: Event) -> Unit) {
            binding.run {
                binding.root.setOnClickListener { clickListener(event) }
                binding.startDateTv.text = event.startDate
                binding.endDateTv.text = event.endDate
                binding.eventNameTv.text = event.name
                Picasso.get()
                    .load(event.icon)
                    .fit()
                    .centerCrop()
                    .into(binding.eventIv)
            }
        }
    }
}