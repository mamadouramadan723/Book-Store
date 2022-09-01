package com.rmd.media.bookstore.framework

import android.content.Context
import com.rmd.media.bookstore.data.BookmarkDataSource
import com.rmd.media.bookstore.domain.Bookmark
import com.rmd.media.bookstore.domain.Document
import com.rmd.media.bookstore.framework.db.BookmarkEntity
import com.rmd.media.bookstore.framework.db.MajesticReaderDatabase

class RoomBookmarkDataSource(context: Context) : BookmarkDataSource {

  // 1
  private val bookmarkDao = MajesticReaderDatabase.getInstance(context).bookmarkDao()

  // 2
  override suspend fun add(document: Document, bookmark: Bookmark) =
      bookmarkDao.addBookmark(
          BookmarkEntity(
          documentUri = document.url,
          page = bookmark.page
      )
      )

  override suspend fun read(document: Document): List<Bookmark> = bookmarkDao
      .getBookmarks(document.url).map { Bookmark(it.id, it.page) }

  override suspend fun remove(document: Document, bookmark: Bookmark) =
      bookmarkDao.removeBookmark(
          BookmarkEntity(id = bookmark.id, documentUri = document.url, page = bookmark.page)
      )
}