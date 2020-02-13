package by.itechart.internship

import java.time.temporal.ChronoUnit

import by.itechart.internship.BikeStats.logger
import org.slf4j.LoggerFactory
import org.slf4s.Logger

object GeneralStats {
  private lazy val logger = Logger(LoggerFactory.getLogger(this.getClass))

  def logicController(configValues: ConfigValues, dataTableOfTrips: List[Array[NewTypes.BikeInfo]]): Unit = {
    val listOfGeneralStats = parserGeneralStats(configValues, dataTableOfTrips)
    preparingDataForWriting(configValues, listOfGeneralStats)
  }

  private def parserGeneralStats(configValues: ConfigValues, dataTableOfTrips: List[Array[NewTypes.BikeInfo]]): Array[NewTypes.BikeInfo] = {
    logger.debug("Getting GeneralStats from data...")
    val counterRow = dataTableOfTrips.length;
    val theLongestTrip = dataTableOfTrips.map(line => (ChronoUnit.MINUTES.between
    (
      Converter.convertToDate(line(Columns.startTimeColumnIndex.id)),
      Converter.convertToDate(line(Columns.stopTimeColumnIndex.id)))
      )).max
    val uniqueBikes = dataTableOfTrips.map(line => line(Columns.bikeIdColumnIndex.id)).distinct.length
    val percentMales = dataTableOfTrips.count(line => line.last == configValues.genderMenValue) * 100.0f / counterRow
    val percentFemales = dataTableOfTrips.count(line => line.last == configValues.genderWomenValue) * 100.0f / counterRow
    val numberOfEmptyValues = dataTableOfTrips.map(list => list.count(_ == "")).sum

    val listOfGeneralStats = Array(counterRow.toString, theLongestTrip.toString,
      uniqueBikes.toString, percentMales.toString, percentFemales.toString, numberOfEmptyValues.toString)
    listOfGeneralStats
  }

  private def preparingDataForWriting(configValues: ConfigValues, listOfGeneralStats: Array[NewTypes.BikeInfo]): Unit = {
    logger.debug("Preparing data for writing...")
    val strPath = configValues.pathFilesStats + configValues.pathFileGeneralStats
    val fieldsCSV = Array("number of trips", "the longest trip", "unique bikes",
      "percentage of men", "percentage of women", "number of empty values")
    val listOfRecords = List(fieldsCSV, listOfGeneralStats)
    FileWriter.tableWriter(listOfRecords, strPath)
  }
}
