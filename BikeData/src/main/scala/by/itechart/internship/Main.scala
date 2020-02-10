package by.itechart.internship

import com.typesafe.config.ConfigFactory

object Main extends App {
  val config = ConfigFactory.load("lightbend.conf")
  val table = ReaderFile.tableWriter(config)
  val gp = GlobalParam(config, table)

  //GeneralStats.parserGeneralStats(gp)
  UsageStats.parserUsageStats(gp)
  //BikeStats.parserBikeStats(gp)
}
