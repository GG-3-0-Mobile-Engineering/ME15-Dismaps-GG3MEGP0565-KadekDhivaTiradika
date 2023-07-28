package com.bydhiva.dismaps.common.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bydhiva.dismaps.R
import com.bydhiva.dismaps.domain.model.Province

class ListSuggestionAdapter: ListAdapter<Province,ListSuggestionAdapter.SuggestionViewHolder>(SuggestionComparator()) {
    private lateinit var onItemClickCallback: OnItemClickCallback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_suggestion, parent, false)
        return SuggestionViewHolder(view)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onBindViewHolder(holder: SuggestionViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClickCallback)
    }

    class SuggestionViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bind(suggestion: Province, onItemClickCallback: OnItemClickCallback) {
            itemView.findViewById<TextView>(R.id.tv_suggestion).text = suggestion.name
            itemView.setOnClickListener {
                onItemClickCallback.onClick(suggestion)
            }
        }

    }

    class SuggestionComparator: DiffUtil.ItemCallback<Province>() {
        override fun areItemsTheSame(oldItem: Province, newItem: Province): Boolean {
            return oldItem.code == newItem.code
        }

        override fun areContentsTheSame(oldItem: Province, newItem: Province): Boolean {
            return oldItem.name == newItem.name
        }
    }

    interface OnItemClickCallback{
        fun onClick(suggestion: Province)
    }
}