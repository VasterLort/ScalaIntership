package by.itechart.internship.parsing

import java.time.temporal.ChronoUnit

import by.itechart.internship.config.LightBendConfig
import by.itechart.internship.dao.TripInfo
import by.itechart.internship.types.StatsInfo
import by.itechart.internship.types.enums.GenderEnum
import org.slf4j.LoggerFactory
import org.slf4s.Logger

object GeneralStats {
  private lazy val logger = Logger(LoggerFactory.getLogger(this.getClass))

  def controlLogicGeneralStatsParsing(configValues: LightBendConfig, dataTableOfTrips: List[TripInfo]): StatsInfo = {
    val listOfGeneralStats = parseGeneralStats(configValues, dataTableOfTrips)
    prepareDataForWriting(configValues, listOfGeneralStats)

  }

  private def parseGeneralStats(configValues: LightBendConfig, dataTableOfTrips: List[TripInfo]): Array[String] = {
    logger.debug("Getting GeneralStats from data...")
    val counterRow = dataTableOfTrips.length;
    val theLongestTrip = dataTableOfTrips.map(trip => (ChronoUnit.MINUTES.between
    (
      Converter.convertToDate(trip.start_time),
      Converter.convertToDate(trip.end_time))
      )).max
    val uniqueBikes = dataTableOfTrips.map(trip => trip.bike_id).distinct.length
    val percentMales = dataTableOfTrips.count(trip => trip.gender == GenderEnum.male) * 100.0f / counterRow
    val percentFemales = dataTableOfTrips.count(trip => trip.gender == GenderEnum.female) * 100.0f / counterRow
    val numberOfEmptyValues = dataTableOfTrips.count(trip => trip == null)

    Array(counterRow.toString, theLongestTrip.toString,
      uniqueBikes.toString, percentMales.toString, percentFemales.toString, numberOfEmptyValues.toString)
  }

  private def prepareDataForWriting(configValues: LightBendConfig, listOfGeneralStats: Array[String]): StatsInfo = {
    logger.debug("Preparing data for writing...")
    val strPath = configValues.pathFilesStats + configValues.pathFileGeneralStats
    val fieldsCSV = Array("number of trips", "the longest trip", "unique bikes",
      "percentage of men", "percentage of women", "number of empty values")
    val listOfStats = StatsInfo(List(fieldsCSV, listOfGeneralStats), strPath)
    listOfStats
  }
}
