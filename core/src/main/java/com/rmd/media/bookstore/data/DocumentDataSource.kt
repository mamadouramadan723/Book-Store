package com.rmd.media.bookstore.data

import com.rmd.media.bookstore.domain.Document

interface DocumentDataSource {

  suspend fun add(document: Document)

  suspend fun readAll(): List<Document>

  suspend fun remove(document: Document)
}