package by.itechart.internship

import org.slf4j.LoggerFactory
import org.slf4s.Logger

import scala.io.{BufferedSource, Source}

object FileReader {
  val logger = Logger(LoggerFactory.getLogger(this.getClass))
  logger.debug("Reading CSV-file")

  type BikeInfo = String

  def tableReader(configValues: ConfigValues): BufferedSource = {
    Source.fromURL(getClass.getResource(configValues.pathFileTripData))
  }
}
