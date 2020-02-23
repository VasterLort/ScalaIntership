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

  def writeToCSVFile(configValues: LightBendConfig, listOfRecords: List[StatsInfo]): Unit = {
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
          case f@Failure(_) =>
            Try(csvWriter.close()).recoverWith {
              case _ =>
                logger.debug("Problem with writing data to CSV-file ... = " + f)
                f
            }
          case success => success
        }
      )
    )
  }
}
