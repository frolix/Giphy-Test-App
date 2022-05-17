package com.example.giphytestapp.presintation.fragment.viewGiphy

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.model.GiphyItem
import com.example.giphytestapp.databinding.ItemTrendingGiphyBinding

class GiphyViewAdapter :
    PagingDataAdapter<GiphyItem, GiphyiewHolder>(
        GiphyDiffCallBack()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GiphyiewHolder {
        return GiphyiewHolder(
            ItemTrendingGiphyBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: GiphyiewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }
}

class GiphyDiffCallBack : DiffUtil.ItemCallback<GiphyItem>() {
    override fun areItemsTheSame(
        oldItem: GiphyItem,
        newItem: GiphyItem
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: GiphyItem,
        newItem: GiphyItem
    ): Boolean {
        return oldItem == newItem
    }
}

class GiphyiewHolder(
    private val binding: ItemTrendingGiphyBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(path: GiphyItem?) {
        path?.let { itGiphy ->

            binding.description.text = itGiphy.title

            Glide.with(itemView.context).asGif().load(itGiphy.images.previewGif.url)
                .into(binding.imageViewTrendingGiphy)

        }
    }

}