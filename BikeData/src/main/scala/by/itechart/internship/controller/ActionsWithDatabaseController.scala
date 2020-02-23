package by.itechart.internship.controller

import by.itechart.internship.dao.{StationDAO, UserInfoDAO, _}
import by.itechart.internship.types.{ColumnsEnum, GenderEnum, NewTypes, UserTypeEnum}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object ActionsWithDatabaseController {
  private val bikeDAO = new BikeDAO("database")
  private val stationDAO = new StationDAO("database")
  private val userInfoDAO = new UserInfoDAO("database")
  private val tripDAO = new TripDAO("database")
  private val tripInfoDAO = new TripInfoDAO("database")

  def controlLogicDeletingAndInserting(dataOfTrips: List[Array[NewTypes.BikeInfo]]): Future[List[Unit]] = {
    val actionResult = Future.reduce(List(deleteAllData(dataOfTrips), insertAllData(dataOfTrips)))(_ ++ _)
    actionResult
  }

  def getTripInfo(): Future[List[TripInfo]] = {
    val actionResult = tripInfoDAO.getAll()
    actionResult
  }

  private def deleteAllData(dataOfTrips: List[Array[NewTypes.BikeInfo]]): Future[List[Unit]] = {
    val actionResult = Future.sequence(List(
      tripDAO.deleteAll(),
      userInfoDAO.deleteAll(),
      stationDAO.deleteAll(),
      bikeDAO.deleteAll()))
    actionResult
  }

  private def insertAllData(dataOfTrips: List[Array[NewTypes.BikeInfo]]): Future[List[Unit]] = {
    val actionResult = Future.sequence(List(
      bikeDAO.insert(PreparingDataForWriting.prepareBikeData(dataOfTrips)),
      stationDAO.insert(PreparingDataForWriting.prepareStationData(dataOfTrips)),
      userInfoDAO.insert(PreparingDataForWriting.prepareUserInfoData(dataOfTrips)),
      tripDAO.insert(PreparingDataForWriting.prepareTripData(dataOfTrips))))
    actionResult
  }

  private object PreparingDataForWriting {
    def prepareStationData(dataOfTrips: List[Array[NewTypes.BikeInfo]]): List[Station] = {
      val startStations = dataOfTrips.map(line =>
        Station(line(ColumnsEnum.starStationIdColumnIndex.id).toLong, line(ColumnsEnum.startStationNameColumnIndex.id),
          line(ColumnsEnum.startStationLatitudeColumnIndex.id).toDouble, line(ColumnsEnum.startStationLongitudeColumnIndex.id) toDouble))

      val endStations = dataOfTrips.map(line =>
        Station(line(ColumnsEnum.endStationIdColumnIndex.id).toLong, line(ColumnsEnum.endStationNameColumnIndex.id),
          line(ColumnsEnum.endStationLatitudeColumnIndex.id).toDouble, line(ColumnsEnum.endStationLongitudeColumnIndex.id) toDouble))

      (startStations ++ endStations).distinct
    }

    def prepareBikeData(dataOfTrips: List[Array[NewTypes.BikeInfo]]): List[Bike] = {
      dataOfTrips.map(line => Bike(line(ColumnsEnum.bikeIdColumnIndex.id).toLong, "Not Value", "Not Value")).distinct
    }

    def prepareUserInfoData(dataOfTrips: List[Array[NewTypes.BikeInfo]]): List[UserInfo] = {
      dataOfTrips.map(line =>
        UserInfo(
          if (line(ColumnsEnum.genderColumnIndex.id) == "1") GenderEnum.male
          else if (line(ColumnsEnum.genderColumnIndex.id) == "2") GenderEnum.female
          else GenderEnum.unknown,
          if (line(ColumnsEnum.userTypeColumnIndex.id) == UserTypeEnum.Customer.toString) UserTypeEnum.Customer
          else UserTypeEnum.Subscriber,
          line(ColumnsEnum.birthYearColumnIndex.id)))
    }

    def prepareTripData(dataOfTrips: List[Array[NewTypes.BikeInfo]]): List[Trip] = {
      dataOfTrips.map(line =>
        Trip(
          line(ColumnsEnum.tripDurationColumnIndex.id).toLong,
          line(ColumnsEnum.starStationIdColumnIndex.id).toLong,
          line(ColumnsEnum.endStationIdColumnIndex.id).toLong,
          line(ColumnsEnum.bikeIdColumnIndex.id).toLong,
          line(ColumnsEnum.startTimeColumnIndex.id),
          line(ColumnsEnum.stopTimeColumnIndex.id)))
    }
  }

}
