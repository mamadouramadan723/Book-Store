package com.rmd.media.bookstore.interactors

import com.rmd.media.bookstore.data.DocumentRepository

class GetOpenDocument(private val documentRepository: DocumentRepository) {
  operator fun invoke() = documentRepository.getOpenDocument()
}
