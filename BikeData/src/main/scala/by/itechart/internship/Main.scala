package by.itechart.internship

import com.typesafe.config.{Config, ConfigFactory}

import scala.io.Source

object Main extends App {
  val config: Config = ConfigFactory.load("lightbend.conf")
  val bufferedSource = Source.fromURL(getClass.getResource(config.getString("url.pathFileTripData")))
  val logic = ParsingLogic(bufferedSource, config)
  logic.generalStats()
  logic.bikeStats()
  logic.usageStats()
  bufferedSource.close
}
