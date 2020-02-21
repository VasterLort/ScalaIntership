package by.itechart.internship.logic

import java.time.temporal.ChronoUnit

import by.itechart.internship.config.LightBendConfig
import by.itechart.internship.entities.TripInfo
import by.itechart.internship.types.NewTypes
import org.slf4j.LoggerFactory
import org.slf4s.Logger

object BikeStats {
  private lazy val logger = Logger(LoggerFactory.getLogger(this.getClass))

  def logicController(configValues: LightBendConfig, dataTableOfTrips: List[TripInfo]): StatsInfo = {
    val listOfBikesStats = parserBikeStats(configValues, dataTableOfTrips)
    preparingDataForWriting(configValues, listOfBikesStats)
  }

  private def parserBikeStats(configValues: LightBendConfig, dataTableOfTrips: List[TripInfo]): Array[String] = {
    logger.debug("Getting BikeStats from data...")
    val groupBikeId: Map[Long, List[(Long, Long)]] = dataTableOfTrips
      .map(trip => (trip.bike_id,
        ChronoUnit.MINUTES.between(
          Converter.convertToDate(trip.start_time),
          Converter.convertToDate(trip.end_time)))
      )
      .groupBy(_._1)
    val numTrip = groupBikeId.mapValues(_.size).toArray
    val timeUsing = groupBikeId.mapValues(_.map(_._2).sum).toArray
    val listOfBikesStats = (numTrip.map(line => line._1), numTrip.map(line => line._2), timeUsing.map(line => line._2)).zipped.toArray.sortBy(-_._2)
    listOfBikesStats.flatMap(row => Array(s"${row._1}, ${row._2}, ${row._3} \n"))
  }

  private def preparingDataForWriting(configValues: LightBendConfig, listOfBikeStats: Array[NewTypes.BikeInfo]): StatsInfo = {
    logger.debug("Preparing data for writing...")
    val strPath = configValues.pathFilesStats + configValues.pathFileBikeStats
    val fieldsCSV = Array("bikeId", "number of trip", "time using")
    val listOfStats = StatsInfo(List(fieldsCSV, listOfBikeStats), strPath)
    listOfStats
  }
}
