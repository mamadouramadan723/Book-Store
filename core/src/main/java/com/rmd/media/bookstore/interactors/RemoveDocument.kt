package com.rmd.media.bookstore.interactors

import com.rmd.media.bookstore.data.DocumentRepository
import com.rmd.media.bookstore.domain.Document

class RemoveDocument(private val documentRepository: DocumentRepository) {
  suspend operator fun invoke(document: Document) = documentRepository.removeDocument(document)
}