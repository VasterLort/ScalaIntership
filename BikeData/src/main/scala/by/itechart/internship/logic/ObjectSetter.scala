package by.itechart.internship.logic

import by.itechart.internship.entities.{Station, StationTable, UserTypeTable}
import by.itechart.internship.types.Columns
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

object ObjectSetter {
  def stationSetter(table: List[Array[String]]): Unit = {
    val messages = TableQuery[StationTable]
    val db = Database.forConfig("database")

    def freshTestData = table.map(line =>
      Station(line(Columns.starStationIdColumnIndex.id).toLong, line(Columns.startStationNameColumnIndex.id),
        line(Columns.startStationLatitudeColumnIndex.id).toDouble, line(Columns.startStationLongitudeColumnIndex.id)toDouble))
      .distinct
      .toSeq

    val insert: DBIO[Option[Int]] = messages ++= freshTestData
    val insertAction: Future[Option[Int]] = db.run(insert)
    val rowCount = Await.result(insertAction, 2.second)
  }
}
