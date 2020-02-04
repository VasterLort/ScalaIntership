package by.itechart

import java.io.{BufferedWriter, FileWriter}
import java.text.SimpleDateFormat
import java.util.{Date, TimeZone}
import java.util.concurrent.TimeUnit

import au.com.bytecode.opencsv.CSVWriter

import scala.collection.JavaConverters._
import scala.collection.mutable.ListBuffer
import scala.io.Source

object Main extends App {
  generalStats()
  usageStats()
  bikeStats()

  //task_4
  def generalStats(): Unit = {
    val table = tableReader()
    val strPath = "/general-stats.csv"
    val counter = table.length;
    val maxTime = longTrip(table)
    val bikes = uniqueBikes(table).length
    val percentMale = table.count(line => line.last.replaceAll("\"", "") == "1") * 100.0f / counter
    val percentFemale = table.count(line => line.last.replaceAll("\"", "") == "2") * 100.0f / counter
    val numberOfEmptyValues = table.map(list => list.find(_.replaceAll("\"", "") == "").size).sum


    val CSVFields = Array("number of trips", "the longest trip", "unique bikes", "percentage of men", "percentage of women", "number of empty values")
    var listOfRecords = new ListBuffer[Array[String]]()
    listOfRecords += CSVFields
    listOfRecords += Array(counter.toString, maxTime.toString, bikes.toString, percentMale.toString, percentFemale.toString, numberOfEmptyValues.toString)

    tableWriter(strPath, listOfRecords)
  }

  //task_4-2 - logic
  private def longTrip(table: List[Array[String]]): Long = {
    var arr: Long = 0
    for (line <- table) {
      arr = arr.max(TimeUnit.MINUTES.convert(convertToDate(line(Column.second.id)).getTime - convertToDate(line(Column.first.id)).getTime, TimeUnit.MILLISECONDS))
    }

    return arr
  }

  //task_4-3 - logic
  type BikeId = String

  private def uniqueBikes(table: List[Array[String]]): List[BikeId] = {
    var col = List[String]()
    table.foreach(line => col = line(Column.eleventh.id).replaceAll("\"", "") :: col)
    col.distinct
  }

  //task_5
  def usageStats(): Unit = {
    val table = tableReader()
    val strPath = "/usage-stats.csv"
    val groupMonth = table.groupBy(line => convertToDate(line(Column.first.id)).getMonth)

    val CSVFields = Array("January", "February", "March", "April", "May",
      "June", "July", "August", "September", "October", "November", "December")

    var listOfRecords = new ListBuffer[Array[String]]()
    listOfRecords += CSVFields
    listOfRecords += Array(groupMonth.mapValues(_.size)(Month.zero.id).toString, groupMonth.mapValues(_.size)(Month.first.id).toString,
      groupMonth.mapValues(_.size)(Month.second.id).toString, groupMonth.mapValues(_.size)(Month.third.id).toString,
      groupMonth.mapValues(_.size)(Month.fourth.id).toString, groupMonth.mapValues(_.size)(Month.fifth.id).toString,
      groupMonth.mapValues(_.size)(Month.sixth.id).toString, groupMonth.mapValues(_.size)(Month.seventh.id).toString,
      groupMonth.mapValues(_.size)(Month.eighth.id).toString, groupMonth.mapValues(_.size)(Month.ninth.id).toString,
      groupMonth.mapValues(_.size)(Month.tenth.id).toString, groupMonth.mapValues(_.size)(Month.eleventh.id).toString)

    tableWriter(strPath, listOfRecords)
  }


  //task_6
  def bikeStats(): Unit = {
    val strPath = "/bike-stats.csv"
    val CSVFields = Array("bikeId", "number of trip" ,"time using")

    val tableCSV = tableReader
    val groupBikeId = tableCSV
      .map(strArr => (strArr(Column.eleventh.id),
        TimeUnit.MINUTES.convert(convertToDate(strArr(Column.second.id)).getTime -
          convertToDate(strArr(Column.first.id)).getTime, TimeUnit.MILLISECONDS)))
      .groupBy(_._1)

    val numTrip = groupBikeId.view.mapValues(_.size).toList
    val timeUsing = groupBikeId.view.mapValues(_.map(_._2).sum).toList
    val bikeStatsList = (numTrip.map(line => line._1), numTrip.map(line => line._2), timeUsing.map(line => line._2)).zipped.toArray

    var listOfRecords = new ListBuffer[Array[String]]()
    listOfRecords += CSVFields
    for(l <- bikeStatsList){
      listOfRecords += Array(l._1.replaceAll("\"", ""), l._2.toString, l._3.toString)
    }

    tableWriter(strPath, listOfRecords)
  }

  private def tableReader(): List[Array[String]] = {
    Source.fromURL(getClass.getResource("/sources/201608-citibike-tripdata.csv"))
      .getLines()
      .map(_.split(","))
      .toList
      .drop(1)
  }

  private def tableWriter(strPath: String, listOfRecords: ListBuffer[Array[String]]): Unit = {
    val out = new BufferedWriter(new FileWriter("D:/Scala/WorkSpace/ScalaIntership/TripData/src/main/resources/sources" + strPath))
    new CSVWriter(out).writeAll(listOfRecords.asJava)
    out.close()
  }

  private def convertToDate(string: String): Date = {
    val inputFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
    inputFormat.setTimeZone(TimeZone.getTimeZone("GMT"))
    inputFormat.parse(string.replaceAll("\"", ""))
  }
}
