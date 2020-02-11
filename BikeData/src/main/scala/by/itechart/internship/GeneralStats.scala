package by.itechart.internship

import java.time.temporal.ChronoUnit

object GeneralStats {
  def parserGeneralStats(configValues: ConfigValues, table: List[Array[String]]): Unit = {
    val strPath = configValues.pathFilesStats + configValues.pathFileGeneralStats

    val counterRow = table.length;
    val theLongestTrip = table.map(line => (ChronoUnit.MINUTES.between
    (
      Converter.convertToDate(line(Columns.startTimeColumnIndex.id)),
      Converter.convertToDate(line(Columns.stopTimeColumnIndex.id)))
      )).max
    val uniqueBikes = table.map(line => line(Columns.bikeIdColumnIndex.id)).distinct.length
    val percentMales = table.count(line => line.last == configValues.genderMenValue) * 100.0f / counterRow
    val percentFemales = table.count(line => line.last == configValues.genderWomenValue) * 100.0f / counterRow
    val numberOfEmptyValues = table.map(list => list.count(_ == "")).sum

    val fieldsCSV = Array("number of trips", "the longest trip", "unique bikes",
      "percentage of men", "percentage of women", "number of empty values")
    val listOfGeneralStats = Array(counterRow.toString, theLongestTrip.toString,
      uniqueBikes.toString, percentMales.toString, percentFemales.toString, numberOfEmptyValues.toString)
    val listOfRecords = List(fieldsCSV, listOfGeneralStats)

    FileWriter.tableWriter(listOfRecords, strPath)
  }
}
