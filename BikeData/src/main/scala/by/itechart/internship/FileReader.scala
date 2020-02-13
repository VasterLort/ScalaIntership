package by.itechart.internship

import org.slf4j.LoggerFactory
import org.slf4s.Logger

import scala.io.{BufferedSource, Source}

object FileReader {
  private lazy val logger = Logger(LoggerFactory.getLogger(this.getClass))

  def tableReader(configValues: ConfigValues): BufferedSource = {
    logger.debug("Reading data from CSV-file...")
    Source.fromURL(getClass.getResource(configValues.pathFileTripData))
  }
}
