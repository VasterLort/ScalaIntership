package by.itechart.internship

import com.typesafe.config.ConfigFactory

import scala.util.{Failure, Success, Try}

object Main extends App {
  val config = ConfigFactory.load("lightbend.conf")
  val configValues = ConfigValues(config.getString("url.pathFilesStats"),
    config.getString("url.pathFileTripData"), config.getString("url.pathFileGeneralStats"),
    config.getString("url.pathFileUsageStats"), config.getString("url.pathFileBikeStats"),
    config.getInt("fileCSV.nameColumnsIndex"), config.getString("fileCSV.genderMenValue"),
    config.getString("fileCSV.genderWomenValue"), config.getString("fileCSV.delimiterOfFile"))

  Try(FileReader.tableReader(configValues)) match {
    case Success(data) => {
      val table = data.getLines()
        .map(_.replaceAll("\"", "").split(configValues.delimiterOfFile))
        .toList
        .drop(configValues.nameColumnsIndex)

      GeneralStats.parserGeneralStats(configValues, table)
      UsageStats.parserUsageStats(configValues, table)
      BikeStats.parserBikeStats(configValues, table)
    }
    case Failure(e) => println(s"An error has occured, cause: $e")
  }
}
