package com.rmd.media.bookstore.data

import com.rmd.media.bookstore.domain.Bookmark
import com.rmd.media.bookstore.domain.Document

class BookmarkRepository(private val dataSource: BookmarkDataSource) {
  suspend fun addBookmark(document: Document, bookmark: Bookmark) =
      dataSource.add(document, bookmark)

  suspend fun getBookmarks(document: Document) = dataSource.read(document)

  suspend fun removeBookmark(document: Document, bookmark: Bookmark) =
      dataSource.remove(document, bookmark)
}