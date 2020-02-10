package by.itechart.internship

import java.io.{BufferedWriter, FileWriter}

import au.com.bytecode.opencsv.CSVWriter

import scala.collection.JavaConverters._

object WriterFile {
  type BikeInfo = String

  def tableWriter(listOfRecords: List[Array[BikeInfo]], strPath: String): Unit = {
    val out = new BufferedWriter(new FileWriter(strPath))
    new CSVWriter(out).writeAll(listOfRecords.asJava)
    out.close()
  }
}
