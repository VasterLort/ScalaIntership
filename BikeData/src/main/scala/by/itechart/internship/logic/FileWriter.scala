package by.itechart.internship.logic

import java.io.{BufferedWriter, FileWriter}

import au.com.bytecode.opencsv.CSVWriter
import by.itechart.internship.types.NewTypes
import org.slf4j.LoggerFactory
import org.slf4s.Logger

import scala.collection.JavaConverters._
import scala.util.{Failure, Try}

object FileWriter {
  private lazy val logger = Logger(LoggerFactory.getLogger(this.getClass))

  def tableWriter(listOfRecords: List[Array[NewTypes.BikeInfo]], strPath: String): Unit = {
    logger.debug("Writing data to CSV-file...")

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
