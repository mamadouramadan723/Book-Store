package com.rmd.media.bookstore.framework

import android.app.Application
import com.rmd.media.bookstore.data.BookmarkRepository
import com.rmd.media.bookstore.data.DocumentRepository
import com.rmd.media.bookstore.framework.db.InMemoryOpenDocumentDataSource
import com.rmd.media.bookstore.interactors.*

class MajesticReaderApplication : Application() {

  override fun onCreate() {
    super.onCreate()

    val bookmarkRepository = BookmarkRepository(RoomBookmarkDataSource(this))
    val documentRepository = DocumentRepository(
        RoomDocumentDataSource(this),
        InMemoryOpenDocumentDataSource()
    )

    MajesticViewModelFactory.inject(
        this,
        Interactors(
            AddBookmark(bookmarkRepository),
            GetBookmarks(bookmarkRepository),
            RemoveBookmark(bookmarkRepository),
            AddDocument(documentRepository),
            GetDocuments(documentRepository),
            RemoveDocument(documentRepository),
            GetOpenDocument(documentRepository),
            SetOpenDocument(documentRepository)
        )
    )
  }

}