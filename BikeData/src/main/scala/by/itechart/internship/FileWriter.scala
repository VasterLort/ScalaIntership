package by.itechart.internship

import java.io.{BufferedWriter, FileWriter}

import au.com.bytecode.opencsv.CSVWriter

import scala.collection.JavaConverters._
import scala.util.{Failure, Try}

object FileWriter {
  type BikeInfo = String

  def tableWriter(listOfRecords: List[Array[BikeInfo]], strPath: String): Unit = {
    Try(new CSVWriter(new BufferedWriter(new FileWriter(strPath)))).flatMap((csvWriter: CSVWriter) =>
      Try {
        csvWriter.writeAll(
          listOfRecords.asJava
        )
        csvWriter.close()
      } match {
        case f@Failure(_) =>
          Try(csvWriter.close()).recoverWith {
            case _ => f
          }
        case success => success
      }
    )
  }
}
