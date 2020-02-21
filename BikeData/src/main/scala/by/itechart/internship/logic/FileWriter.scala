package by.itechart.internship.logic

import java.io.{BufferedWriter, FileWriter}

import au.com.bytecode.opencsv.CSVWriter
import by.itechart.internship.config.LightBendConfig
import org.slf4j.LoggerFactory
import org.slf4s.Logger

import scala.collection.JavaConverters._
import scala.util.{Failure, Try}

object FileWriter {
  private lazy val logger = Logger(LoggerFactory.getLogger(this.getClass))

  def tableWriter(configValues: LightBendConfig, listOfRecords: List[StatsInfo]): Unit = {
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
                logger.debug("Error ... = " + f)
                f
            }
          case success => success
        }
      )
    )
  }
}
