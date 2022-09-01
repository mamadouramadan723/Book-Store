package com.rmd.media.bookstore.interactors

import com.rmd.media.bookstore.data.BookmarkRepository
import com.rmd.media.bookstore.domain.Bookmark
import com.rmd.media.bookstore.domain.Document

class RemoveBookmark(private val bookmarksRepository: BookmarkRepository) {
  suspend operator fun invoke(document: Document, bookmark: Bookmark) = bookmarksRepository
      .removeBookmark(document, bookmark)
}