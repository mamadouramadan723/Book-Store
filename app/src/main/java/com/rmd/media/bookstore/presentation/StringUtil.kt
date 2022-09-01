package com.rmd.media.bookstore.presentation

import java.text.DecimalFormat
import kotlin.math.log10
import kotlin.math.pow

object StringUtil {

  /**
   * Convenience method for formatting file size.
   * Taken from: https://stackoverflow.com/a/5599842/2914696
   */
  fun readableFileSize(size: Int): String {
    if (size <= 0) {
      return "0"
    }

    val units = arrayOf("B", "kB", "MB", "GB", "TB")
    val digitGroups = (log10(size.toDouble()) / log10(1024.0)).toInt()

    return DecimalFormat("#,##0.#").format(
        size / 1024.0.pow(digitGroups.toDouble())
    ) + " " + units[digitGroups]
  }
}