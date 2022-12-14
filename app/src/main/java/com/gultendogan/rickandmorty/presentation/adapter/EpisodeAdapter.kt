package com.gultendogan.rickandmorty.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gultendogan.rickandmorty.R
import com.gultendogan.rickandmorty.databinding.EpisodeRowLayoutBinding
import com.gultendogan.rickandmorty.domain.uimodel.EpisodeUIModel
import com.gultendogan.rickandmorty.presentation.episode.EpisodeItemClickListener

class EpisodeAdapter(var listener: EpisodeItemClickListener) :
    PagingDataAdapter<EpisodeUIModel, EpisodeAdapter.EpisodeViewHolder>(DiffUtilCallBack()) {
    class EpisodeViewHolder(var binding: EpisodeRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(episode: EpisodeUIModel,listener: EpisodeItemClickListener) {
            binding.episode = episode
            binding.listener = listener
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val view: EpisodeRowLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.episode_row_layout, parent, false
        )
        return EpisodeViewHolder(view)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val currentEpisode = getItem(position)!!
        holder.bind(currentEpisode,listener)
        val hb = holder.binding
        val mContext = hb.root.context
    }
    class DiffUtilCallBack : DiffUtil.ItemCallback<EpisodeUIModel>() {
        override fun areItemsTheSame(
            oldItem: EpisodeUIModel,
            newItem: EpisodeUIModel
        ): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(
            oldItem: EpisodeUIModel,
            newItem: EpisodeUIModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}