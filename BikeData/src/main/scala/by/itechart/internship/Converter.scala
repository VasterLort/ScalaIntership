package by.itechart.internship

import java.time.LocalDateTime
import java.time.format.DateTimeFormatterBuilder

object Converter {
  private lazy val dateFormat = new DateTimeFormatterBuilder()
    .parseCaseInsensitive()
    .appendPattern("M/d/yyyy")
    .appendLiteral(' ')
    .appendPattern("HH:mm:ss")
    .toFormatter()


  def convertToDate(string: String): LocalDateTime = {
    val dateUpdate = LocalDateTime.parse(string, dateFormat)
    dateUpdate
  }
}
