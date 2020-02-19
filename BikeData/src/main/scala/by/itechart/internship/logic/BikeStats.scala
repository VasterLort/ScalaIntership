package by.itechart.internship.logic

import java.time.temporal.ChronoUnit

import by.itechart.internship.config.LightBendConfig
import by.itechart.internship.entities.Trip
import by.itechart.internship.types.NewTypes
import org.slf4j.LoggerFactory
import org.slf4s.Logger

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._

object BikeStats {
  private lazy val logger = Logger(LoggerFactory.getLogger(this.getClass))

  def logicController(configValues: LightBendConfig, dataTableOfTrips: Future[List[Trip]]): Unit = {
    dataTableOfTrips.map {
      vector =>
        val listOfBikesStats = parserBikeStats(configValues, vector)
        preparingDataForWriting(configValues, listOfBikesStats)
    }
  }

  private def parserBikeStats(configValues: LightBendConfig, dataTableOfTrips: List[Trip]): Array[String] = {
    logger.debug("Getting BikeStats from data...")
    println(dataTableOfTrips.length)
    val groupBikeId: Map[Long, List[(Long, Long)]] = dataTableOfTrips
      .map(trip => (trip.bikeId,
        ChronoUnit.MINUTES.between(
          Converter.convertToDate(trip.startTime),
          Converter.convertToDate(trip.endTime)))
      )
      .groupBy(_._1)
    logger.debug("2")
    val numTrip = groupBikeId.mapValues(_.size).toArray
    val timeUsing = groupBikeId.mapValues(_.map(_._2).sum).toArray
    val listOfBikesStats = (numTrip.map(line => line._1), numTrip.map(line => line._2), timeUsing.map(line => line._2)).zipped.toArray.sortBy(-_._2)
    val r = listOfBikesStats.flatMap(row => Array(s"${row._1}, ${row._2}, ${row._3} \n"))
    r.foreach(println)
    r
  }

  private def preparingDataForWriting(configValues: LightBendConfig, listOfBikeStats: Array[NewTypes.BikeInfo]): Unit = {
    logger.debug("Preparing data for writing...")
    val strPath = configValues.pathFilesStats + configValues.pathFileBikeStats
    val fieldsCSV = Array("bikeId", "number of trip", "time using")
    val listOfRecords = List(fieldsCSV, listOfBikeStats)
    FileWriter.tableWriter(listOfRecords, strPath)
  }
}
