package by.itechart.internship

import scala.util.{Failure, Success, Try}

object Main extends App {
  val configValues = LightBendConfig.configSetter
  Try(FileReader.tableReader(configValues)) match {
    case Success(data) => {
      val dataTableOfTrips = data.getLines()
        .map(_.replaceAll("\"", "").split(configValues.delimiterOfFile))
        .toList
        .drop(configValues.nameColumnsIndex)

      GeneralStats.logicController(configValues, dataTableOfTrips)
      UsageStats.logicController(configValues, dataTableOfTrips)
      BikeStats.logicController(configValues, dataTableOfTrips)
    }
    case Failure(e) => println(s"An error has occured, cause: $e")
  }
}
