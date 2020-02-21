package by.itechart.internship.logic

import java.time.Month

import by.itechart.internship.config.LightBendConfig
import by.itechart.internship.entities.TripInfo
import by.itechart.internship.types.NewTypes
import org.slf4j.LoggerFactory
import org.slf4s.Logger

object UsageStats {
  type MonthInfo = String

  private lazy val logger = Logger(LoggerFactory.getLogger(this.getClass))

  def logicController(configValues: LightBendConfig, dataTableOfTrips: List[TripInfo]): StatsInfo = {
    val listOfMonthsStats = parserUsageStats(configValues, dataTableOfTrips)
    preparingDataForWriting(configValues, listOfMonthsStats)

  }

  private def parserUsageStats(configValues: LightBendConfig, dataTableOfTrips: List[TripInfo]): Array[NewTypes.BikeInfo] = {
    logger.debug("Getting UsageStats from data...")
    val groupMonth = dataTableOfTrips
      .groupBy(trip => Converter.convertToDate(trip.start_time).getMonth.getValue)

    val arrayMonths = Array(
      Month.JANUARY, Month.FEBRUARY, Month.MARCH, Month.APRIL,
      Month.MAY, Month.JUNE, Month.JULY, Month.AUGUST, Month.SEPTEMBER,
      Month.OCTOBER, Month.NOVEMBER, Month.DECEMBER
    )
    checkMonth(groupMonth, arrayMonths)
  }

  private def preparingDataForWriting(configValues: LightBendConfig, listOfMonthsStats: Array[NewTypes.BikeInfo]): StatsInfo = {
    logger.debug("Preparing data for writing...")
    val strPath = configValues.pathFilesStats + configValues.pathFileUsageStats
    val fieldsCSV = Array("January", "February", "March", "April", "May",
      "June", "July", "August", "September", "October", "November", "December")
    val listOfStats = StatsInfo(List(fieldsCSV, listOfMonthsStats), strPath)
    listOfStats
  }

  private def checkMonth(groupMonth: Map[Int, List[TripInfo]], arrayMonths: Array[Month]): Array[MonthInfo] = {
    arrayMonths.map(x => groupMonth.mapValues(_.size).get(x.getValue).map(_.toString).getOrElse("Not Value"))
  }
}
