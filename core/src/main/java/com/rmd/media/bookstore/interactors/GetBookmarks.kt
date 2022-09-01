package com.rmd.media.bookstore.interactors

import com.rmd.media.bookstore.data.BookmarkRepository
import com.rmd.media.bookstore.domain.Document

class GetBookmarks(private val bookmarkRepository: BookmarkRepository) {

  suspend operator fun invoke(document: Document) = bookmarkRepository.getBookmarks(document)
}