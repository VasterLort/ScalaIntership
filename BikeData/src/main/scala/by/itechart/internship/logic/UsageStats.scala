package by.itechart.internship.logic

import java.time.Month

import by.itechart.internship.config.LightBendConfig
import by.itechart.internship.types.{Columns, NewTypes}
import org.slf4j.LoggerFactory
import org.slf4s.Logger

object UsageStats {
  type MonthInfo = String

  private lazy val logger = Logger(LoggerFactory.getLogger(this.getClass))

  def logicController(configValues: LightBendConfig, dataTableOfTrips: List[Array[NewTypes.BikeInfo]]): Unit = {
    val listOfMonthsStats = parserUsageStats(configValues, dataTableOfTrips)
    preparingDataForWriting(configValues, listOfMonthsStats)
  }

  private def parserUsageStats(configValues: LightBendConfig, dataTableOfTrips: List[Array[NewTypes.BikeInfo]]): Array[NewTypes.BikeInfo] = {
    logger.debug("Getting UsageStats from data...")
    val groupMonth = dataTableOfTrips
      .groupBy(line => Converter.convertToDate(line(Columns.startTimeColumnIndex.id)).getMonth.getValue)

    val arrayMonths = Array(
      Month.JANUARY, Month.FEBRUARY, Month.MARCH, Month.APRIL,
      Month.MAY, Month.JUNE, Month.JULY, Month.AUGUST, Month.SEPTEMBER,
      Month.OCTOBER, Month.NOVEMBER, Month.DECEMBER
    )
    val listOfMonthsStats = checkMonth(groupMonth, arrayMonths)
    listOfMonthsStats
  }

  private def preparingDataForWriting(configValues: LightBendConfig, listOfMonthsStats: Array[NewTypes.BikeInfo]): Unit = {
    logger.debug("Preparing data for writing...")
    val strPath = configValues.pathFilesStats + configValues.pathFileUsageStats
    val fieldsCSV = Array("January", "February", "March", "April", "May",
      "June", "July", "August", "September", "October", "November", "December")
    val listOfRecords = List(fieldsCSV, listOfMonthsStats)
    FileWriter.tableWriter(listOfRecords, strPath)
  }

  private def checkMonth(groupMonth: Map[Int, List[Array[MonthInfo]]], arrayMonths: Array[Month]): Array[MonthInfo] = {
    val numOfMonths = arrayMonths.map(x => groupMonth.mapValues(_.size).get(x.getValue).map(_.toString).getOrElse("Not Value"))
    numOfMonths
  }
}
