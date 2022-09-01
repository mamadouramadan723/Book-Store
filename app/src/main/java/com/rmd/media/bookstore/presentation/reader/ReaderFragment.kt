package com.rmd.media.bookstore.presentation.reader

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Point
import android.graphics.drawable.BitmapDrawable
import android.graphics.pdf.PdfRenderer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.rmd.media.bookstore.R
import com.rmd.media.bookstore.databinding.FragmentReaderBinding
import com.rmd.media.bookstore.domain.Document
import com.rmd.media.bookstore.presentation.IntentUtil
import com.rmd.media.bookstore.presentation.library.LibraryFragment

class ReaderFragment : Fragment() {

    companion object {

        fun newInstance(document: Document) = ReaderFragment().apply {
            arguments = ReaderViewModel.createArguments(document)
        }
    }

    private lateinit var viewModel: ReaderViewModel
    private lateinit var binding: FragmentReaderBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_reader, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentReaderBinding.bind(view)
        val adapter = BookmarksAdapter {
            viewModel.openBookmark(it)
        }

        binding.bookRecyclerview.adapter = adapter

        viewModel = ViewModelProvider(requireActivity())[ReaderViewModel::class.java]

        viewModel.document.observe(viewLifecycleOwner, Observer {
            if (it == Document.EMPTY) {
                // Show file picker action.
                startActivityForResult(
                    IntentUtil.createOpenIntent(),
                    LibraryFragment.READ_REQUEST_CODE
                )
            }
        })

        viewModel.bookmarks.observe(viewLifecycleOwner, Observer {
            adapter.update(it)
        })

        viewModel.isBookmarked.observe(viewLifecycleOwner, Observer {
            val bookmarkDrawable = if (it) R.drawable.ic_bookmark else R.drawable.ic_bookmark_border
            binding.tabBookmark.setCompoundDrawablesWithIntrinsicBounds(0, bookmarkDrawable, 0, 0)
        })

        viewModel.isInLibrary.observe(viewLifecycleOwner, Observer {
            val libraryDrawable = if (it) R.drawable.ic_library else R.drawable.ic_library_border
            binding.tabLibrary.setCompoundDrawablesRelativeWithIntrinsicBounds(
                0,
                libraryDrawable,
                0,
                0
            )
        })

        viewModel.currentPage.observe(viewLifecycleOwner, Observer { showPage(it) })
        viewModel.hasNextPage.observe(
            viewLifecycleOwner,
            Observer { binding.tabNextPage.isEnabled = it })
        viewModel.hasPreviousPage.observe(
            viewLifecycleOwner,
            Observer { binding.tabPreviousPage.isEnabled = it })

        if (savedInstanceState == null) {
            viewModel.loadArguments(arguments)
        } else {
            // Recreating fragment after configuration change, reopen current page so it can be rendered again.
            viewModel.reopenPage()
        }

        binding.tabBookmark.setOnClickListener { viewModel.toggleBookmark() }
        binding.tabLibrary.setOnClickListener { viewModel.toggleInLibrary() }
        binding.tabNextPage.setOnClickListener { viewModel.nextPage() }
        binding.tabPreviousPage.setOnClickListener { viewModel.previousPage() }
    }

    private fun showPage(page: PdfRenderer.Page) {
        binding.pageIv.visibility = View.VISIBLE
        binding.pagesTv.visibility = View.VISIBLE
        binding.tabPreviousPage.visibility = View.VISIBLE
        binding.tabNextPage.visibility = View.VISIBLE

        if (binding.pageIv.drawable != null) {
            (binding.pageIv.drawable as BitmapDrawable).bitmap.recycle()
        }

        val size = Point()
        activity?.windowManager?.defaultDisplay?.getSize(size)

        val pageWidth = size.x
        val pageHeight = page.height * pageWidth / page.width

        val bitmap = Bitmap.createBitmap(
            pageWidth,
            pageHeight,
            Bitmap.Config.ARGB_8888
        )

        page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
        binding.pageIv.setImageBitmap(bitmap)

        binding.pagesTv.text = getString(
            R.string.page_navigation_format,
            page.index + 1,
            viewModel.renderer.value?.pageCount
        )

        page.close()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // Process open file intent.
        if (requestCode == LibraryFragment.READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.data?.also { uri -> viewModel.openDocument(uri) }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

}
