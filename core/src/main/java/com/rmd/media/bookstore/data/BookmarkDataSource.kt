package com.rmd.media.bookstore.data

import com.rmd.media.bookstore.domain.Bookmark
import com.rmd.media.bookstore.domain.Document

interface BookmarkDataSource {

  suspend fun add(document: Document, bookmark: Bookmark)

  suspend fun read(document: Document): List<Bookmark>

  suspend fun remove(document: Document, bookmark: Bookmark)
}