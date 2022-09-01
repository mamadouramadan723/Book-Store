package com.rmd.media.bookstore.presentation.library

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.rmd.media.bookstore.R
import com.rmd.media.bookstore.databinding.FragmentLibraryBinding
import com.rmd.media.bookstore.databinding.FragmentReaderBinding
import com.rmd.media.bookstore.framework.MajesticViewModelFactory
import com.rmd.media.bookstore.presentation.IntentUtil.createOpenIntent
import com.rmd.media.bookstore.presentation.MainActivityDelegate

class LibraryFragment : Fragment() {

    companion object {
        const val READ_REQUEST_CODE = 100

        fun newInstance() = LibraryFragment()
    }

    private lateinit var viewModel: LibraryViewModel
    private lateinit var mainActivityDelegate: MainActivityDelegate
    private lateinit var binding: FragmentLibraryBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            mainActivityDelegate = context as MainActivityDelegate
        } catch (e: ClassCastException) {
            throw ClassCastException()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_library, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLibraryBinding.bind(view)
        val adapter = DocumentsAdapter(glide = Glide.with(this)) {
            mainActivityDelegate.openDocument(it)
        }

        binding.documentRecyclerview.adapter = adapter

        viewModel =
            ViewModelProvider(requireActivity())[LibraryViewModel::class.java]

        viewModel.documents.observe(viewLifecycleOwner, Observer { adapter.update(it) })
        viewModel.loadDocuments()

        binding.fab.setOnClickListener {
            startActivityForResult(
                createOpenIntent(),
                READ_REQUEST_CODE
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // Process open file intent.
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.data?.also { uri -> viewModel.addDocument(uri) }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

}
