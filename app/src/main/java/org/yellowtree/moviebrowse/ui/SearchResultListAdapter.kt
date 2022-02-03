package org.yellowtree.moviebrowse.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.yellowtree.moviebrowse.R
import org.yellowtree.moviebrowse.databinding.SearchResultItemBinding
import org.yellowtree.moviebrowse.model.SearchResult

class SearchResultListAdapter(private val isFav : Boolean, val itemClickCallback : ItemClickListener, private val favCallback : (SearchResult, Boolean)->Unit) : ListAdapter<SearchResult, SearchResultViewHolder>(object : DiffUtil.ItemCallback<SearchResult>() {

    override fun areItemsTheSame(oldItem: SearchResult, newItem: SearchResult): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SearchResult, newItem: SearchResult): Boolean {
        return oldItem.title == newItem.title && oldItem.year == newItem.year
    }

}) {

    private var favs = listOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        return SearchResultViewHolder(DataBindingUtil.inflate<SearchResultItemBinding>(LayoutInflater.from(parent.context),
        R.layout.search_result_item,
        parent,
        false))
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        val searchResult = getItem(position)
        holder.binding.searchResult = searchResult
        holder.binding.checkBox.apply {
            if (isFav) {
                isSelected = true
            } else {
                isSelected = favs.contains(searchResult.id)
            }
        }
        holder.binding.cardView.setOnClickListener {
            itemClickCallback.onMovieItemClick(searchResult.id)
        }

        holder.binding.checkBox.setOnClickListener {

            if (it.isSelected) {
                it.isSelected = false
                favCallback.invoke(searchResult, false)
            } else {
                it.isSelected = true
                favCallback.invoke(searchResult, true)
            }

        }
        holder.binding.executePendingBindings()


    }

    fun setFavs(favs : List<String>) {
        this.favs = favs
        notifyDataSetChanged()
    }
}


class SearchResultViewHolder(val binding : SearchResultItemBinding) : RecyclerView.ViewHolder(binding.root)


interface ItemClickListener {
    fun onMovieItemClick(id : String)
}