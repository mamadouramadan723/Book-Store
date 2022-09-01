package com.rmd.media.bookstore.presentation.reader

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rmd.media.bookstore.R
import com.rmd.media.bookstore.databinding.ItemBookmarkBinding
import com.rmd.media.bookstore.domain.Bookmark

class BookmarksAdapter(
    private val bookmarks: MutableList<Bookmark> = mutableListOf(),
    private val itemClickListener: (Bookmark) -> Unit
) : RecyclerView.Adapter<BookmarksAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_bookmark, parent, false)
        )
    }

    override fun getItemCount() = bookmarks.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = ItemBookmarkBinding.bind(holder.itemView)
        holder.itemView.apply {
            binding.bookmarkTv.text = holder.itemView.resources.getString(
                R.string.page_bookmark_format,
                bookmarks[position].page
            )
        }

        holder.itemView.setOnClickListener { itemClickListener.invoke(bookmarks[position]) }
    }

    fun update(newBookmarks: List<Bookmark>) {
        bookmarks.clear()
        bookmarks.addAll(newBookmarks)

        notifyDataSetChanged()
    }
}