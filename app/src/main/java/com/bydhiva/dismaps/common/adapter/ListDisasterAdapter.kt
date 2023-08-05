package com.bydhiva.dismaps.common.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bydhiva.dismaps.R
import com.bydhiva.dismaps.databinding.ItemDisasterBinding
import com.bydhiva.dismaps.domain.model.Disaster
import com.bydhiva.dismaps.utils.changeDrawable
import com.bydhiva.dismaps.utils.getColorId
import com.bydhiva.dismaps.utils.getDisasterIconId
import com.bydhiva.dismaps.utils.getStringId
import com.bydhiva.dismaps.utils.loadImage
import com.bydhiva.dismaps.utils.toShortText

class ListDisasterAdapter: ListAdapter<Disaster, ListDisasterAdapter.DisasterViewHolder>(DisasterComparator()) {
    private lateinit var onItemClickCallback: OnItemClickCallback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DisasterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_disaster, parent, false)
        return DisasterViewHolder(view)
    }

    override fun onBindViewHolder(holder: DisasterViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClickCallback)
    }

    override fun submitList(list: List<Disaster>?) {
        super.submitList(list)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    class DisasterViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding by lazy {
            ItemDisasterBinding.bind(itemView)
        }

        fun bind(disaster: Disaster, onItemClickCallback: OnItemClickCallback) {
            binding.apply {
                tvTitle.text = disaster.title.ifBlank { itemView.context.getString(disaster.disasterType.getStringId()) }
                tvDesc.text = disaster.text
                tvDisasterType.text = itemView.context.getString(disaster.disasterType.getStringId())
                tvDisasterType.setTextColor(ContextCompat.getColor(itemView.context, disaster.disasterType.getColorId()))
                ivDisaster.loadImage(disaster.imageUrl, disaster.disasterType)
                ivIconDisaster.changeDrawable(disaster.disasterType.getDisasterIconId())
                tvDate.text = disaster.createdAt?.toShortText()
            }
            itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(disaster)
            }
        }

    }

    class DisasterComparator: DiffUtil.ItemCallback<Disaster>() {
        override fun areItemsTheSame(oldItem: Disaster, newItem: Disaster): Boolean {
            return oldItem.pKey == newItem.pKey
        }

        override fun areContentsTheSame(oldItem: Disaster, newItem: Disaster): Boolean {
            return oldItem.title == newItem.title
                    && oldItem.text == newItem.text
                    && oldItem.imageUrl == newItem.imageUrl
                    && oldItem.disasterType == newItem.disasterType
        }
    }

    interface OnItemClickCallback{
        fun onItemClicked(disaster: Disaster)
    }
}