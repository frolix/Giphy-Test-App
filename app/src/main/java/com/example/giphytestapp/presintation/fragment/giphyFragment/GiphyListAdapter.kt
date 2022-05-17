package com.example.giphytestapp.presintation.fragment.giphyFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.model.GiphyItem
import com.example.giphytestapp.databinding.ItemGiphyListBinding

class GiphyListAdapter(
    private var clickListener: GiphyViewHolder.OnImageViewClickListener,
    private var clickClickFavoriteListener: GiphyViewHolder.OnClickFavoriteClickListener,
) :
    PagingDataAdapter<GiphyItem, GiphyViewHolder>(
        GiphyDiffCallBack()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GiphyViewHolder {
        return GiphyViewHolder(
            ItemGiphyListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            clickListener,
            clickClickFavoriteListener
        )
    }

    override fun onBindViewHolder(holder: GiphyViewHolder, position: Int) {
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

class GiphyViewHolder(
    private val binding: ItemGiphyListBinding,
    private var clickListener: OnImageViewClickListener,
    private var clickFavoriteListener: OnClickFavoriteClickListener,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(path: GiphyItem?) {
        path?.let { itGiphy ->


            Glide.with(itemView.context).load(itGiphy.images.original.url)
                .into(binding.imgThumbnail)
            binding.description.text = itGiphy.title
            binding.download.setOnClickListener {
                clickFavoriteListener.onClickFavoriteViewListener(itGiphy)
            }

            itemView.setOnClickListener {
                clickListener.onClickImageViewListener(itGiphy,absoluteAdapterPosition)
            }

        }
    }

    interface OnImageViewClickListener {
        fun onClickImageViewListener(
            item: GiphyItem,
            position: Int
        ) {
        }
    }

    interface OnClickFavoriteClickListener {
        fun onClickFavoriteViewListener(
            item: GiphyItem
        ) {
        }
    }

}