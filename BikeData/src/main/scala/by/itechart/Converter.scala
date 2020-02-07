package by.itechart

import java.text.SimpleDateFormat
import java.util.Date

object Converter {
  def convertToDate(string: String): Date = {
    val inputFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
    inputFormat.parse(string)
  }
}
