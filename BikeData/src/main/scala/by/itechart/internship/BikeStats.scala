package by.itechart.internship

import java.util.concurrent.TimeUnit

object BikeStats {
  def parserBikeStats(gp: GlobalParam): Unit = {
    val strPath = gp.config.getString("url.pathFilesStats") +
      gp.config.getString("url.pathFileBikeStats")
    val fieldsCSV = Array("bikeId", "number of trip", "time using")

    val groupBikeId = gp.table
      .map(strArr => (strArr(Columns.bikeIdColumnIndex.id),
        TimeUnit.MINUTES.convert(
          Converter.convertToDate(strArr(Columns.stopTimeColumnIndex.id)).getTime -
            Converter.convertToDate(strArr(Columns.startTimeColumnIndex.id)).getTime, TimeUnit.MILLISECONDS))
      )
      .groupBy(_._1)

    val numTrip = groupBikeId.mapValues(_.size).toArray
    val timeUsing = groupBikeId.mapValues(_.map(_._2).sum).toArray


    val listOfMerge = (numTrip.map(line => line._1), numTrip.map(line => line._2), timeUsing.map(line => line._2)).zipped.toArray.sortBy(-_._2)
    val listOfRecords = List(fieldsCSV, listOfMerge.flatMap(row => Array(row._1 + " " + row._2 + " " + row._3 + " " + '\n')))

    WriterFile.tableWriter(listOfRecords, strPath)
  }
}
