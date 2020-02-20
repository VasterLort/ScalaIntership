package by.itechart.internship.logic

import by.itechart.internship.entities._
import by.itechart.internship.types.{ColumnsEnum, GenderEnum, NewTypes, UserTypeEnum}
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

object DatabaseWriter {
  def logicController(dataTableOfTrips: List[Array[NewTypes.BikeInfo]]): Unit = {
    deleteData()
    stationSetter(dataTableOfTrips)
    bikeSetter(dataTableOfTrips)
    userInfoSetter(dataTableOfTrips)
    tripSetter(dataTableOfTrips)
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

    val userInfoValues = dataTableOfTrips.map(line =>
      UserInfo(
        if (line(ColumnsEnum.genderColumnIndex.id) == "1") GenderEnum.male
        else if (line(ColumnsEnum.genderColumnIndex.id) == "2") GenderEnum.female
        else GenderEnum.unknown,
        if (line(ColumnsEnum.userTypeColumnIndex.id) == UserTypeEnum.Customer.toString) UserTypeEnum.Customer
        else UserTypeEnum.Subscriber,
        line(ColumnsEnum.birthYearColumnIndex.id)))

    val insert: DBIO[Option[Int]] = messages ++= userInfoValues
    insertData(insert)
  }

  private def tripSetter(dataTableOfTrips: List[Array[NewTypes.BikeInfo]]): Unit = {
    val messages = TableQuery[TripTable]

    val userInfoValues = dataTableOfTrips.map(line =>
      Trip(
        line(ColumnsEnum.tripDurationColumnIndex.id).toLong,
        line(ColumnsEnum.starStationIdColumnIndex.id).toLong,
        line(ColumnsEnum.endStationIdColumnIndex.id).toLong,
        line(ColumnsEnum.bikeIdColumnIndex.id).toLong,
        line(ColumnsEnum.startTimeColumnIndex.id),
        line(ColumnsEnum.stopTimeColumnIndex.id)))
    println(userInfoValues.length)
    val insert: DBIO[Option[Int]] = messages ++= userInfoValues
    insertData(insert)
  }

  private def insertData(insert: DBIO[Option[Int]]): Unit = {
    val db = Database.forConfig("database")
    val insertAction: Future[Option[Int]] = db.run(insert)
    val rowCount = Await.result(insertAction, 720.second)
    db.close()
  }

  private def deleteData(): Unit = {
    val db = Database.forConfig("database")

    Await.result(db.run(TableQuery[TripTable].delete), 120.seconds)
    Await.result(db.run(TableQuery[UserInfoTable].delete), 120.seconds)
    Await.result(db.run(TableQuery[StationTable].delete), 120.seconds)
    Await.result(db.run(TableQuery[BikeTable].delete), 120.seconds)
    db.close()
  }
}
