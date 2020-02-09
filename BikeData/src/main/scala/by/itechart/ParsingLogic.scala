package by.itechart

import java.io.{BufferedWriter, FileWriter}
import java.time.Month
import java.util.concurrent.TimeUnit

import au.com.bytecode.opencsv.CSVWriter
import com.typesafe.config.Config

import scala.collection.JavaConverters._
import scala.io.BufferedSource

case class ParsingLogic(bufferedSource: BufferedSource, config: Config) {
  type BikeInfo = String

  private val table = bufferedSource
    .getLines()
    .map(_.replaceAll("\"", "").split(","))
    .toList
    .drop(Columns.recordIdColumnIndexFirst.id)

  //task_4
  def generalStats(): Unit = {
    val strPath = config.getString("url.pathFileGeneralStats")

    val counterRow = table.length;
    val theLongestTrip = table.map(line => (TimeUnit.MINUTES.convert
    (
      Converter.convertToDate(line(Columns.recordIdColumnIndexSecond.id)).getTime -
        Converter.convertToDate(line(Columns.recordIdColumnIndexFirst.id)).getTime, TimeUnit.MILLISECONDS)
      )).max
    val uniqueBikes = table.map(line => line(Columns.recordIdColumnIndexEleventh.id)).distinct.length
    val percentMale = table.count(line => line.last == "1") * 100.0f / counterRow
    val percentFemale = table.count(line => line.last == "2") * 100.0f / counterRow
    val numberOfEmptyValues = table.map(list => list.count(_ == "")).sum

    val fieldsCSV = Array("number of trips", "the longest trip", "unique bikes",
      "percentage of men", "percentage of women", "number of empty values")
    val listOfGeneralStats = Array(counterRow.toString, theLongestTrip.toString,
      uniqueBikes.toString, percentMale.toString, percentFemale.toString, numberOfEmptyValues.toString)
    val listOfRecords = List(fieldsCSV, listOfGeneralStats)

    tableWriter(listOfRecords, strPath)
  }

  //task_5
  def usageStats(): Unit = {
    val strPath = config.getString("url.pathFileUsageStats")
    val groupMonth = table.groupBy(line => (Converter.convertToDate(line(Columns.recordIdColumnIndexFirst.id)).getMonth + 1))

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

    tableWriter(listOfRecords, strPath)
  }


  //task_6
  def bikeStats(): Unit = {
    val strPath = config.getString("url.pathFileBikeStats")
    val fieldsCSV = Array("bikeId", "number of trip", "time using")

    val groupBikeId = table
      .map(strArr => (strArr(Columns.recordIdColumnIndexEleventh.id),
        TimeUnit.MINUTES.convert(
          Converter.convertToDate(strArr(Columns.recordIdColumnIndexSecond.id)).getTime -
            Converter.convertToDate(strArr(Columns.recordIdColumnIndexFirst.id)).getTime, TimeUnit.MILLISECONDS))
      )
      .groupBy(_._1)

    val numTrip = groupBikeId.mapValues(_.size).toList
    val timeUsing = groupBikeId.mapValues(_.map(_._2).sum).toList

    val listOfMerge = (numTrip.map(line => line._1), numTrip.map(line => line._2.toString), timeUsing.map(line => line._2.toString)).zipped.toArray
    val listOfRecords = List(fieldsCSV, listOfMerge.map(x => (x._1 + " " + x._2 + " " + x._3) + "\n"))

    tableWriter(listOfRecords, strPath)
  }

  private def tableWriter(listOfRecords: List[Array[BikeInfo]], strPath: String): Unit = {
    val resourcesPath = config.getString("url.pathFilesStats")
    val out = new BufferedWriter(new FileWriter(resourcesPath + strPath))
    new CSVWriter(out).writeAll(listOfRecords.asJava)
    out.close()
  }
}
