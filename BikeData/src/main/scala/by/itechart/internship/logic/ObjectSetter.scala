package by.itechart.internship.logic

import by.itechart.internship.entities._
import by.itechart.internship.types.{ColumnsEnum, NewTypes}
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

object ObjectSetter {
  def logicController(dataTableOfTrips: List[Array[NewTypes.BikeInfo]]): Unit = {
    //stationSetter(dataTableOfTrips)
    //bikeSetter(dataTableOfTrips)
    userInfoSetter(dataTableOfTrips)
  }

  private def stationSetter(dataTableOfTrips: List[Array[NewTypes.BikeInfo]]): Unit = {
    val messages = TableQuery[StationTable]

    val startStationValues = dataTableOfTrips.map(line =>
      Station(line(ColumnsEnum.starStationIdColumnIndex.id).toLong, line(ColumnsEnum.startStationNameColumnIndex.id),
        line(ColumnsEnum.startStationLatitudeColumnIndex.id).toDouble, line(ColumnsEnum.startStationLongitudeColumnIndex.id) toDouble))

    val endStationValues = dataTableOfTrips.map(line =>
      Station(line(ColumnsEnum.endStationIdColumnIndex.id).toLong, line(ColumnsEnum.endStationNameColumnIndex.id),
        line(ColumnsEnum.endStationLatitudeColumnIndex.id).toDouble, line(ColumnsEnum.endStationLongitudeColumnIndex.id) toDouble))

    val stationValues = (startStationValues ++ endStationValues).distinct
    val insert: DBIO[Option[Int]] = messages ++= stationValues
    insertData(insert)
  }

  private def bikeSetter(dataTableOfTrips: List[Array[NewTypes.BikeInfo]]): Unit = {
    val messages = TableQuery[BikeTable]

    val bikeValues = dataTableOfTrips.map(line =>
      Bike(line(ColumnsEnum.bikeIdColumnIndex.id).toLong, "Not Value", "Not Value")).distinct

    val insert: DBIO[Option[Int]] = messages ++= bikeValues
    insertData(insert)
  }

  private def userInfoSetter(dataTableOfTrips: List[Array[NewTypes.BikeInfo]]): Unit = {
    val messages = TableQuery[UserInfoTable]

    val userInfoValues = Array(UserInfo(
      Gender.unknown,
      "Subscriber",
      "2222"))

    val insert: DBIO[Option[Int]] = messages ++= userInfoValues
    insertData(insert)
  }

  private def insertData(insert: DBIO[Option[Int]]): Unit = {
    val db = Database.forConfig("database")
    val insertAction: Future[Option[Int]] = db.run(insert)
    val rowCount = Await.result(insertAction, 360.second)
  }
}
