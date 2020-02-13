package by.itechart.internship

import java.time.Month

import org.slf4j.LoggerFactory
import org.slf4s.Logger

object UsageStats {
  type MonthInfo = String

  private lazy val logger = Logger(LoggerFactory.getLogger(this.getClass))

  def logicController(configValues: ConfigValues, dataTableOfTrips: List[Array[NewTypes.BikeInfo]]): Unit = {
    val listOfMonthsStats = parserUsageStats(configValues, dataTableOfTrips)
    preparingDataForWriting(configValues, listOfMonthsStats)
  }

  private def parserUsageStats(configValues: ConfigValues, dataTableOfTrips: List[Array[NewTypes.BikeInfo]]): Array[NewTypes.BikeInfo] = {
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

  private def preparingDataForWriting(configValues: ConfigValues, listOfMonthsStats: Array[NewTypes.BikeInfo]): Unit = {
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
