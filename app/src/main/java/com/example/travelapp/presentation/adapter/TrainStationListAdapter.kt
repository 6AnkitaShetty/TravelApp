package com.example.travelapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.travelapp.data.model.Payload
import com.example.travelapp.databinding.ItemTrainStationBinding

class TrainStationListAdapter :
    RecyclerView.Adapter<TrainStationListAdapter.TrainStationListAdapterViewHolder>() {

    class TrainStationListAdapterViewHolder(private val binding: ItemTrainStationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Payload) {
            binding.model = item
            binding.executePendingBindings()
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<Payload>() {
        override fun areItemsTheSame(oldEntity: Payload, newEntity: Payload): Boolean {
            return oldEntity.UICCode == newEntity.UICCode
        }

        override fun areContentsTheSame(oldEntity: Payload, newEntity: Payload): Boolean {
            return oldEntity == newEntity
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrainStationListAdapterViewHolder {
        val binding =
            ItemTrainStationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return TrainStationListAdapterViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: TrainStationListAdapterViewHolder, position: Int) {
        val city = differ.currentList[position]
        holder.bind(city)
    }
}