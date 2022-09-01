package com.rmd.media.bookstore.presentation

import com.rmd.media.bookstore.domain.Document


interface MainActivityDelegate {

  fun openDocument(document: Document)
}