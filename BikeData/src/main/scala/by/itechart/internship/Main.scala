package by.itechart.internship

import by.itechart.internship.config.{FlywayConfig, LightBendConfig}
import by.itechart.internship.dao.DatabaseReader
import by.itechart.internship.logic._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

object Main extends App {
  val configValues = LightBendConfig.configSetter
  val flywayConfig = FlywayConfig.initDatabaseStructure(configValues)

  val result = FileReader.tableReader(configValues).map(_ => {
    val dataTrips = DatabaseReader.writer().map{vector =>
      val listOfRecords = List(
        GeneralStats.logicController(configValues, vector),
        UsageStats.logicController(configValues, vector),
        BikeStats.logicController(configValues, vector))
      FileWriter.tableWriter(configValues, listOfRecords)
    }
    Await.result(dataTrips, 720.seconds)
  })

  Await.result(result, 720.seconds)
}
