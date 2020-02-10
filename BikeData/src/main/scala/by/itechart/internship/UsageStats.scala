package by.itechart.internship

import java.time.Month

object UsageStats {
  def parserUsageStats(gp: GlobalParam): Unit = {
    val strPath = gp.config.getString("url.pathFilesStats") +
      gp.config.getString("url.pathFileUsageStats")
    val groupMonth = gp.table
      .groupBy(line => Converter.convertToDate(line(Columns.startTimeColumnIndex.id)).getMonth.getValue)

    val fieldsCSV = Array("January", "February", "March", "April", "May",
      "June", "July", "August", "September", "October", "November", "December")
    val listOfUsageStats = Array(
      checkMonth(groupMonth, Month.JANUARY.getValue),
      checkMonth(groupMonth, Month.FEBRUARY.getValue),
      checkMonth(groupMonth, Month.MARCH.getValue),
      checkMonth(groupMonth, Month.APRIL.getValue),
      checkMonth(groupMonth, Month.MAY.getValue),
      checkMonth(groupMonth, Month.JUNE.getValue),
      checkMonth(groupMonth, Month.JULY.getValue),
      checkMonth(groupMonth, Month.AUGUST.getValue),
      checkMonth(groupMonth, Month.SEPTEMBER.getValue),
      checkMonth(groupMonth, Month.OCTOBER.getValue),
      checkMonth(groupMonth, Month.NOVEMBER.getValue),
      checkMonth(groupMonth, Month.DECEMBER.getValue))
    val listOfRecords = List(fieldsCSV, listOfUsageStats)

    WriterFile.tableWriter(listOfRecords, strPath)
  }

  private def checkMonth(groupMonth: Map[Int, List[Array[String]]], numOfMonth: Int): String ={
    println(numOfMonth)
    println(groupMonth.mapValues(_.size).withDefault("Not found"))
    groupMonth.mapValues(_.size).get(numOfMonth).map(_.toString).getOrElse("Not found")
  }
}
