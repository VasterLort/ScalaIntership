package by.itechart.internship.logic

import by.itechart.internship.config.LightBendConfig
import org.slf4j.LoggerFactory
import org.slf4s.Logger

import scala.io.Source
import scala.util.{Failure, Success, Try}

object FileReader {
  private lazy val logger = Logger(LoggerFactory.getLogger(this.getClass))

  def tableReader(configValues: LightBendConfig): Unit = {
    logger.debug("Reading data from CSV-file...")
    Try(Source.fromURL(getClass.getResource(configValues.pathFileTripData))) match {
      case Success(data) => {
        val dataTableOfTrips = data.getLines()
          .map(_.replaceAll("\"", "").split(configValues.delimiterOfFile))
          .toList
          .drop(configValues.nameColumnsIndex)

        DatabaseWriter.logicController(dataTableOfTrips)
      }
      case Failure(e) => println(s"An error has occured, cause: $e")
    }
  }
}
