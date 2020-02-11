package by.itechart.internship

import java.time.Month

object UsageStats {
  def parserUsageStats(configValues: ConfigValues, table: List[Array[String]]): Unit = {
    val strPath = configValues.pathFilesStats + configValues.pathFileUsageStats
    val groupMonth = table
      .groupBy(line => Converter.convertToDate(line(Columns.startTimeColumnIndex.id)).getMonth.getValue)

    val fieldsCSV = Array("January", "February", "March", "April", "May",
      "June", "July", "August", "September", "October", "November", "December")
    val arrayMonths = Array(
      Month.JANUARY, Month.FEBRUARY, Month.MARCH, Month.APRIL,
      Month.MAY, Month.JUNE, Month.JULY, Month.AUGUST, Month.SEPTEMBER,
      Month.OCTOBER, Month.NOVEMBER, Month.DECEMBER
    )
    val listOfRecords = List(fieldsCSV, checkMonth(groupMonth, arrayMonths))

    FileWriter.tableWriter(listOfRecords, strPath)
  }

  private def checkMonth(groupMonth: Map[Int, List[Array[String]]], arrayMonths: Array[Month]): Array[String] = {
    val numOfMonths = arrayMonths.map(x => groupMonth.mapValues(_.size).get(x.getValue).map(_.toString).getOrElse("Not Value"))
    numOfMonths
  }
}
