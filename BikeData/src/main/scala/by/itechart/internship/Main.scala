package by.itechart.internship

import by.itechart.internship.config.{FlywayConfig, LightBendConfig}
import by.itechart.internship.logic._

object Main extends App {
  val configValues = LightBendConfig.configSetter
  val flywayConfig = FlywayConfig.initDatabaseStructure(configValues)

  FileReader.tableReader(configValues)

  val dataTrips = DatabaseReader.writer()
  GeneralStats.logicController(configValues, dataTrips)
  UsageStats.logicController(configValues, dataTrips)
  BikeStats.logicController(configValues, dataTrips)

  Thread.sleep(36000)
}
