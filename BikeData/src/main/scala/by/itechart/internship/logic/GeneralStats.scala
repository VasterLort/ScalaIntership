package by.itechart.internship.logic

import java.time.temporal.ChronoUnit

import by.itechart.internship.config.LightBendConfig
import by.itechart.internship.entities.Trip
import by.itechart.internship.types.NewTypes
import org.slf4j.LoggerFactory
import org.slf4s.Logger

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object GeneralStats {
  private lazy val logger = Logger(LoggerFactory.getLogger(this.getClass))

  def logicController(configValues: LightBendConfig, dataTableOfTrips: Future[List[Trip]]): Unit = {
    dataTableOfTrips.map {
      vector =>
        val listOfGeneralStats = parserGeneralStats(configValues, vector)
        preparingDataForWriting(configValues, listOfGeneralStats)
    }
  }

  private def parserGeneralStats(configValues: LightBendConfig, dataTableOfTrips: List[Trip]): Array[NewTypes.BikeInfo] = {
    logger.debug("Getting GeneralStats from data...")
    val counterRow = dataTableOfTrips.length;
    val theLongestTrip = dataTableOfTrips.map(trip => (ChronoUnit.MINUTES.between
    (
      Converter.convertToDate(trip.startTime),
      Converter.convertToDate(trip.endTime))
      )).max
    val uniqueBikes = dataTableOfTrips.map(trip => trip.bikeId).distinct.length
    val percentMales = 60 * 100.0f / counterRow //dataTableOfTrips.count(line => line.last == configValues.genderMenValue) * 100.0f / counterRow
    val percentFemales = 45 * 100.0f / counterRow //dataTableOfTrips.count(line => line.last == configValues.genderWomenValue) * 100.0f / counterRow
    val numberOfEmptyValues = 54 //dataTableOfTrips.map(list => list.count(_ == "")).sum

    val listOfGeneralStats = Array(counterRow.toString, theLongestTrip.toString,
      uniqueBikes.toString, percentMales.toString, percentFemales.toString, numberOfEmptyValues.toString)
    listOfGeneralStats
  }

  private def preparingDataForWriting(configValues: LightBendConfig, listOfGeneralStats: Array[NewTypes.BikeInfo]): Unit = {
    logger.debug("Preparing data for writing...")
    val strPath = configValues.pathFilesStats + configValues.pathFileGeneralStats
    val fieldsCSV = Array("number of trips", "the longest trip", "unique bikes",
      "percentage of men", "percentage of women", "number of empty values")
    val listOfRecords = List(fieldsCSV, listOfGeneralStats)
    FileWriter.tableWriter(listOfRecords, strPath)
  }
}
