package com.rmd.media.bookstore.framework.db

import com.rmd.media.bookstore.data.OpenDocumentDataSource
import com.rmd.media.bookstore.domain.Document


class InMemoryOpenDocumentDataSource : OpenDocumentDataSource {

  private var openDocument: Document = Document.EMPTY

  override fun setOpenDocument(document: Document) {
    openDocument = document
  }

  override fun getOpenDocument() = openDocument
}