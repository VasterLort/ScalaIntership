package by.itechart.internship

import java.time.Month

object UsageStats {
  def parserUsageStats(gp: GlobalParam): Unit = {
    val strPath = gp.config.getString("url.pathFilesStats") +
      gp.config.getString("url.pathFileUsageStats")
    val groupMonth = gp.table.groupBy(line => (Converter.convertToDate(line(Columns.startTimeColumnIndex.id)).getMonth + 1))

    val fieldsCSV = Array("January", "February", "March", "April", "May",
      "June", "July", "August", "September", "October", "November", "December")
    val listOfUsageStats = Array(
      groupMonth.mapValues(_.size)(Month.JANUARY.getValue).toString, groupMonth.mapValues(_.size)(Month.FEBRUARY.getValue).toString,
      groupMonth.mapValues(_.size)(Month.MARCH.getValue).toString, groupMonth.mapValues(_.size)(Month.APRIL.getValue).toString,
      groupMonth.mapValues(_.size)(Month.MAY.getValue).toString, groupMonth.mapValues(_.size)(Month.JUNE.getValue).toString,
      groupMonth.mapValues(_.size)(Month.JULY.getValue).toString, groupMonth.mapValues(_.size)(Month.AUGUST.getValue).toString,
      groupMonth.mapValues(_.size)(Month.SEPTEMBER.getValue).toString, groupMonth.mapValues(_.size)(Month.OCTOBER.getValue).toString,
      groupMonth.mapValues(_.size)(Month.NOVEMBER.getValue).toString, groupMonth.mapValues(_.size)(Month.DECEMBER.getValue).toString)
    val listOfRecords = List(fieldsCSV, listOfUsageStats)

    WriterFile.tableWriter(listOfRecords, strPath)
  }
}
