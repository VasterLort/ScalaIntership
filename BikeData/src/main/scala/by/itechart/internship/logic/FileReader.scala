package by.itechart.internship.logic

import by.itechart.internship.config.LightBendConfig
import org.slf4j.LoggerFactory
import org.slf4s.Logger

import scala.io.{BufferedSource, Source}

object FileReader {
  private lazy val logger = Logger(LoggerFactory.getLogger(this.getClass))

  def tableReader(configValues: LightBendConfig): BufferedSource = {
    logger.debug("Reading data from CSV-file...")
    Source.fromURL(getClass.getResource(configValues.pathFileTripData))
  }
}
