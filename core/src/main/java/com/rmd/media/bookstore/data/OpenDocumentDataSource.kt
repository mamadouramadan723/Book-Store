package com.rmd.media.bookstore.data

import com.rmd.media.bookstore.domain.Document

interface OpenDocumentDataSource {

  fun setOpenDocument(document: Document)

  fun getOpenDocument(): Document
}