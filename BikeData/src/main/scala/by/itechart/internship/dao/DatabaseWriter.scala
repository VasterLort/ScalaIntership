package by.itechart.internship.dao

import by.itechart.internship.entities._
import by.itechart.internship.logic.MyPostgresDriver.api._
import by.itechart.internship.types.{ColumnsEnum, GenderEnum, NewTypes, UserTypeEnum}

import scala.concurrent.Future

object DatabaseWriter {
  def logicController(dataTableOfTrips: List[Array[NewTypes.BikeInfo]]): Future[Option[Int]] = {
    val station = stationSetter(dataTableOfTrips)
    val bike = bikeSetter(dataTableOfTrips)
    val userInfo = userInfoSetter(dataTableOfTrips)
    val trip = tripSetter(dataTableOfTrips)
    val insert: DBIO[Option[Int]] = station andThen bike andThen userInfo andThen trip
    insertData(insert)
  }

  private def stationSetter(dataTableOfTrips: List[Array[NewTypes.BikeInfo]]): DBIO[Option[Int]] = {
    val messages = TableQuery[StationTable]

    val startStationValues = dataTableOfTrips.map(line =>
      Station(line(ColumnsEnum.starStationIdColumnIndex.id).toLong, line(ColumnsEnum.startStationNameColumnIndex.id),
        line(ColumnsEnum.startStationLatitudeColumnIndex.id).toDouble, line(ColumnsEnum.startStationLongitudeColumnIndex.id) toDouble))

    val endStationValues = dataTableOfTrips.map(line =>
      Station(line(ColumnsEnum.endStationIdColumnIndex.id).toLong, line(ColumnsEnum.endStationNameColumnIndex.id),
        line(ColumnsEnum.endStationLatitudeColumnIndex.id).toDouble, line(ColumnsEnum.endStationLongitudeColumnIndex.id) toDouble))

    val stationValues = (startStationValues ++ endStationValues).distinct
    val insert: DBIO[Option[Int]] = messages ++= stationValues
    insert
  }

  private def bikeSetter(dataTableOfTrips: List[Array[NewTypes.BikeInfo]]): DBIO[Option[Int]] = {
    val messages = TableQuery[BikeTable]

    val bikeValues = dataTableOfTrips.map(line =>
      Bike(line(ColumnsEnum.bikeIdColumnIndex.id).toLong, "Not Value", "Not Value")).distinct

    val insert: DBIO[Option[Int]] = messages ++= bikeValues
    insert
  }

  private def userInfoSetter(dataTableOfTrips: List[Array[NewTypes.BikeInfo]]): DBIO[Option[Int]] = {
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
    insert
  }

  private def tripSetter(dataTableOfTrips: List[Array[NewTypes.BikeInfo]]): DBIO[Option[Int]] = {
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
    insert
  }

  private def insertData(insert: DBIO[Option[Int]]): Future[Option[Int]] = {
    val db = Database.forConfig("database")
    val preparedQuery = db.run(
      (TableQuery[TripTable].delete andThen
        TableQuery[UserInfoTable].delete andThen
        TableQuery[UserInfoTable].delete andThen
        TableQuery[StationTable].delete andThen
        TableQuery[BikeTable].delete andThen
        insert
        ).transactionally)

    preparedQuery
  }
}
