package com.rmd.media.bookstore.presentation.library

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.rmd.media.bookstore.R
import com.rmd.media.bookstore.databinding.ItemDocumentBinding
import com.rmd.media.bookstore.domain.Document
import com.rmd.media.bookstore.presentation.StringUtil

class DocumentsAdapter(
    private val documents: MutableList<Document> = mutableListOf(),
    private val glide: RequestManager,
    private val itemClickListener: (Document) -> Unit
) : RecyclerView.Adapter<DocumentsAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_document, parent, false)
        )
    }

    override fun getItemCount() = documents.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.run {
        val binding = ItemDocumentBinding.bind(holder.itemView)
        holder.itemView.apply {
            glide.load(documents[position].thumbnail)
                .error(glide.load(R.drawable.preview_missing))
                .into(binding.ivPreview)
            binding.tvTitle.text = documents[position].name
            binding.sizeTv?.text = StringUtil.readableFileSize(documents[position].size) ?: ""
        }

        holder.itemView.setOnClickListener { itemClickListener.invoke(documents[position]) }
    }

    fun update(newDocuments: List<Document>) {
        documents.clear()
        documents.addAll(newDocuments)

        notifyDataSetChanged()
    }
}