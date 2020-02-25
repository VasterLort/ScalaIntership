package by.itechart.internship.usingfiles

import java.io.{BufferedWriter, FileWriter}

import au.com.bytecode.opencsv.CSVWriter
import by.itechart.internship.config.LightBendConfig
import by.itechart.internship.types.StatsInfo
import org.slf4j.LoggerFactory
import org.slf4s.Logger

import scala.collection.JavaConverters._
import scala.util.{Failure, Try}

object FileWriter {
  private lazy val logger = Logger(LoggerFactory.getLogger(this.getClass))

  def writeToCSVFile(configValues: LightBendConfig, listOfRecords: List[StatsInfo]): List[Try[String]] = {
    logger.debug("Writing data to CSV-file...")

    listOfRecords.map(list =>
      Try(new CSVWriter(new BufferedWriter(new FileWriter(list.strPath)))).flatMap((csvWriter: CSVWriter) =>
        Try {
          csvWriter.writeAll(
            list.tripInfo.asJava
          )
          logger.debug("Completed...")
          csvWriter.close()
        } match {
          case Failure(f) => {
            csvWriter.close()
            Try("Problem with writing data to CSV-file ... = " + f)
          }
          case Failure(s) =>
            Try("Successful writing data to CSV-file ... = " + s)
        }
      )
    )
  }
}
