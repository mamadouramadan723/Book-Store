package com.rmd.media.bookstore.interactors

import com.rmd.media.bookstore.data.DocumentRepository
import com.rmd.media.bookstore.domain.Document

class SetOpenDocument(private val documentRepository: DocumentRepository) {
  operator fun invoke(document: Document) = documentRepository.setOpenDocument(document)
}