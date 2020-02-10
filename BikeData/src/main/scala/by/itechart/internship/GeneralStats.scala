package by.itechart.internship

import java.util.concurrent.TimeUnit

object GeneralStats {
  def parserGeneralStats(gp: GlobalParam): Unit = {
    val strPath = gp.config.getString("url.pathFilesStats") +
      gp.config.getString("url.pathFileGeneralStats")

    val counterRow = gp.table.length;
    val theLongestTrip = gp.table.map(line => (TimeUnit.MINUTES.convert
    (
      Converter.convertToDate(line(Columns.stopTimeColumnIndex.id)).getTime -
        Converter.convertToDate(line(Columns.startTimeColumnIndex.id)).getTime, TimeUnit.MILLISECONDS)
      )).max
    val uniqueBikes = gp.table.map(line => line(Columns.bikeIdColumnIndex.id)).distinct.length
    val percentMales = gp.table.count(line => line.last == gp.config.getString("fileCSV.genderMenValue")) * 100.0f / counterRow
    val percentFemales = gp.table.count(line => line.last == gp.config.getString("fileCSV.genderWomenValue")) * 100.0f / counterRow
    val numberOfEmptyValues = gp.table.map(list => list.count(_ == "")).sum

    val fieldsCSV = Array("number of trips", "the longest trip", "unique bikes",
      "percentage of men", "percentage of women", "number of empty values")
    val listOfGeneralStats = Array(counterRow.toString, theLongestTrip.toString,
      uniqueBikes.toString, percentMales.toString, percentFemales.toString, numberOfEmptyValues.toString)
    val listOfRecords = List(fieldsCSV, listOfGeneralStats)

    WriterFile.tableWriter(listOfRecords, strPath)
  }
}
