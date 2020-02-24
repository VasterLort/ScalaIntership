package by.itechart.internship

import by.itechart.internship.config.{FlywayConfig, LightBendConfig}
import by.itechart.internship.controller.ActionsWithDatabaseService
import by.itechart.internship.parsing._
import by.itechart.internship.usingfiles.{FileReader, FileWriter}

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object Main extends App {
/*  val configValues = LightBendConfig.setConfigValues
  val flywayConfig = FlywayConfig.initDatabaseStructure(configValues)

  val result = new FileReader().readFromCSVFile(configValues).flatMap(_ => {
    ActionsWithDatabaseService.getTripInfo().map { vector =>
      val listOfRecords = List(
        GeneralStats.controlLogicGeneralStatsParsing(configValues, vector),
        UsageStats.controlLogicUsageStatsParsing(configValues, vector),
        BikeStats.controlLogicBikeStatsParsing(configValues, vector))
      FileWriter.writeToCSVFile(configValues, listOfRecords)
    }
  })

  Await.result(result, 720.seconds)*/
}
