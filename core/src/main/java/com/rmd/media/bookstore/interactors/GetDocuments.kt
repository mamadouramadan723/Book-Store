package com.rmd.media.bookstore.interactors

import com.rmd.media.bookstore.data.DocumentRepository

class GetDocuments(private val documentRepository: DocumentRepository) {
  suspend operator fun invoke() = documentRepository.getDocuments()
}
