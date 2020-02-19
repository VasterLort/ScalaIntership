package by.itechart.internship

import by.itechart.internship.config.{FlywayConfig, LightBendConfig}
import by.itechart.internship.logic.{BikeStats, DatabaseReader, DatabaseWriter, FileReader, GeneralStats}

import scala.util.{Failure, Success, Try}

object Main extends App {
  val configValues = LightBendConfig.configSetter
  val flywayConfig = FlywayConfig.initDatabaseStructure(configValues)
  Try(FileReader.tableReader(configValues)) match {
    case Success(data) => {
      val dataTableOfTrips = data.getLines()
        .map(_.replaceAll("\"", "").split(configValues.delimiterOfFile))
        .toList
        .drop(configValues.nameColumnsIndex)

      //DatabaseWriter.logicController(dataTableOfTrips)
      val dataTrips = DatabaseReader.writer()
      GeneralStats.logicController(configValues, dataTrips)
      //UsageStats.logicController(configValues, dataTrips)
      BikeStats.logicController(configValues, dataTrips)
    }
    case Failure(e) => println(s"An error has occured, cause: $e")
  }

  Thread.sleep(20000000)
}
