package by.itechart.internship

import com.typesafe.config.Config

import scala.io.Source

object ReaderFile {
  type BikeInfo = String

  def tableWriter(config: Config): List[Array[BikeInfo]] = {
    val bufferedSource = Source.fromURL(getClass.getResource(config.getString("url.pathFileTripData")))
    val table = bufferedSource
      .getLines()
      .map(_.replaceAll("\"", "").split(config.getString("fileCSV.keySymbolOfFile")))
      .toList
      .drop(config.getInt("fileCSV.nameColumns"))

    bufferedSource.close
    table
  }
}
