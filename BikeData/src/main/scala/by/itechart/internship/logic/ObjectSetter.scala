package by.itechart.internship.logic

import by.itechart.internship.entities._
import by.itechart.internship.types.{ColumnsEnum, NewTypes}
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

object ObjectSetter {
  def logicController(dataTableOfTrips: List[Array[NewTypes.BikeInfo]]): Unit = {
    stationSetter(dataTableOfTrips)
  }

  private def stationSetter(dataTableOfTrips: List[Array[NewTypes.BikeInfo]]): Unit = {
    val messages = TableQuery[StationTable]

    val startStationsValues = dataTableOfTrips.map(line =>
      Station(line(ColumnsEnum.starStationIdColumnIndex.id).toLong, line(ColumnsEnum.startStationNameColumnIndex.id),
        line(ColumnsEnum.startStationLatitudeColumnIndex.id).toDouble, line(ColumnsEnum.startStationLongitudeColumnIndex.id) toDouble))

    val endStationsValues = dataTableOfTrips.map(line =>
      Station(line(ColumnsEnum.endStationIdColumnIndex.id).toLong, line(ColumnsEnum.endStationNameColumnIndex.id),
        line(ColumnsEnum.endStationLatitudeColumnIndex.id).toDouble, line(ColumnsEnum.endStationLongitudeColumnIndex.id) toDouble))

    val stationValues = (startStationsValues ++ endStationsValues).distinct
    val insert: DBIO[Option[Int]] = messages ++= stationValues
    insertData(insert)
  }

/*  private def bikeSetter(dataTableOfTrips: List[Array[NewTypes.BikeInfo]]): Unit = {
    val messages = TableQuery[BikeTable]

    val bikesValues = dataTableOfTrips.map(line => (
      line(ColumnsEnum.bikeIdColumnIndex.id),
      Converter.convertToDate(line(ColumnsEnum.startTimeColumnIndex.id)),
      Converter.convertToDate(line(ColumnsEnum.stopTimeColumnIndex.id)))
    )
      .groupBy(_._1)
      .mapValues(x => x.map(_._2).min.toString, x.map(_._3).max.toString)

    val insert: DBIO[Option[Int]] = messages ++= bikesValues
    insertData(insert)
  }*/

  /*private def userInfoSetter(dataTableOfTrips: List[Array[NewTypes.BikeInfo]]): Unit = {
    val messages = TableQuery[UserInfoTable]

    val userInfoValues = dataTableOfTrips.map(line =>
      UserInfo.apply(
        line(ColumnsEnum.genderColumnIndex.id).toLong,
        line(ColumnsEnum.userTypeColumnIndex.id).toLong,
        line(ColumnsEnum.birthYearColumnIndex.id).toInt))
      .distinct

    val insert: DBIO[Option[Int]] = messages ++= userInfoValues
    insertData(insert)
  }*/

  private def insertData(insert: DBIO[Option[Int]]): Unit = {
    val db = Database.forConfig("database")
    val insertAction: Future[Option[Int]] = db.run(insert)
    val rowCount = Await.result(insertAction, 2.second)
  }
}
