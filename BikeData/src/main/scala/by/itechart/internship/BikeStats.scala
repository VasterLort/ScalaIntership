package by.itechart.internship

import java.time.temporal.ChronoUnit

object BikeStats {
  def parserBikeStats(configValues: ConfigValues, table: List[Array[String]]): Unit = {
    val strPath = configValues.pathFilesStats + configValues.pathFileBikeStats
    val fieldsCSV = Array("bikeId", "number of trip", "time using")

    val groupBikeId = table
      .map(strArr => (strArr(Columns.bikeIdColumnIndex.id),
        ChronoUnit.MINUTES.between(
          Converter.convertToDate(strArr(Columns.startTimeColumnIndex.id)),
          Converter.convertToDate(strArr(Columns.stopTimeColumnIndex.id))))
      )
      .groupBy(_._1)

    val numTrip = groupBikeId.mapValues(_.size).toArray
    val timeUsing = groupBikeId.mapValues(_.map(_._2).sum).toArray


    val listOfMerge = (numTrip.map(line => line._1), numTrip.map(line => line._2), timeUsing.map(line => line._2)).zipped.toArray.sortBy(-_._2)
    val listOfRecords = List(fieldsCSV, listOfMerge.flatMap(row => Array(row._1 + " " + row._2 + " " + row._3 + " " + '\n')))

    FileWriter.tableWriter(listOfRecords, strPath)
  }
}
