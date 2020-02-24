package by.itechart.internship.usingfiles

import by.itechart.internship.config.LightBendConfig
import by.itechart.internship.controller.ActionsWithDatabaseService
import org.slf4j.LoggerFactory
import org.slf4s.Logger

import scala.concurrent.Future
import scala.io.Source
import scala.util.{Failure, Success, Try}

class FileReader(actionsWithDatabaseService: ActionsWithDatabaseService = new ActionsWithDatabaseService()) {
  private lazy val logger = Logger(LoggerFactory.getLogger(this.getClass))

  def readFromCSVFile(configValues: LightBendConfig): Future[Either[Exception, List[Unit]]] = {
    logger.debug("Reading data from CSV-file...")
    Try(Source.fromURL(getClass.getResource(configValues.pathFileTripData))) match {
      case Success(data) => {
        val dataOfTrips = data.getLines()
          .map(_.replaceAll("\"", "").split(configValues.delimiterOfFile))
          .toList
          .drop(configValues.nameColumnsIndex)

        actionsWithDatabaseService.controlLogicDeletingAndInserting(dataOfTrips)
      }
      case Failure(e) => {
        Future.successful(Right(s Exception("Problem with inserting data to database" + e)
      }
    }
  }
}
