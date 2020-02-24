package by.itechart.internship.service

import by.itechart.internship.config.LightBendConfig
import by.itechart.internship.dao._
import by.itechart.internship.types._
import by.itechart.internship.types.enums.{ColumnsEnum, GenderEnum, UserTypeEnum}
import by.itechart.internship.usingfiles.FileReader

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class DataSavingService(
                         bikeDao: BikeDao = Daos.bikeDao,
                         userInfoDao: UserInfoDao = Daos.userInfoDao,
                         tripDao: TripDao = Daos.tripDao,
                         stationDao: StationDao = Daos.stationDao,
                         tripInfoDao: TripInfoDao = Daos.tripInfoDao
                       ) {

  def controlLogicDeletingAndInsertingData(configValues: LightBendConfig): Future[List[Unit]] = {
    val dataOfTrips = FileReader.readFromCSVFile(configValues)
    dataOfTrips.flatMap { vector =>
      Future.reduce(List(deleteAllData(vector.right.get), insertAllData(vector.right.get)))(_ ++ _)
    }
  }

  private def deleteAllData(dataOfTrips: List[Array[String]]): Future[List[Unit]] = {
    val actionResult = Future.sequence(List(
      tripDao.deleteAll(),
      userInfoDao.deleteAll(),
      stationDao.deleteAll(),
      bikeDao.deleteAll()))
    actionResult
  }

  private def insertAllData(dataOfTrips: List[Array[String]]): Future[List[Unit]] = {
    val actionResult = Future.sequence(List(
      bikeDao.insert(PreparingDataForWriting.prepareBikeData(dataOfTrips)),
      stationDao.insert(PreparingDataForWriting.prepareStationData(dataOfTrips)),
      userInfoDao.insert(PreparingDataForWriting.prepareUserInfoData(dataOfTrips)),
      tripDao.insert(PreparingDataForWriting.prepareTripData(dataOfTrips))))
    actionResult
  }

  private object PreparingDataForWriting {
    def prepareStationData(dataOfTrips: List[Array[String]]): List[Station] = {
      val startStations = dataOfTrips.map(line =>
        Station(line(ColumnsEnum.starStationIdColumnIndex.id).toLong, line(ColumnsEnum.startStationNameColumnIndex.id),
          line(ColumnsEnum.startStationLatitudeColumnIndex.id).toDouble, line(ColumnsEnum.startStationLongitudeColumnIndex.id) toDouble))

      val endStations = dataOfTrips.map(line =>
        Station(line(ColumnsEnum.endStationIdColumnIndex.id).toLong, line(ColumnsEnum.endStationNameColumnIndex.id),
          line(ColumnsEnum.endStationLatitudeColumnIndex.id).toDouble, line(ColumnsEnum.endStationLongitudeColumnIndex.id) toDouble))

      (startStations ++ endStations).distinct
    }

    def prepareBikeData(dataOfTrips: List[Array[String]]): List[Bike] = {
      dataOfTrips.map(line => Bike(line(ColumnsEnum.bikeIdColumnIndex.id).toLong, "Not Value", "Not Value")).distinct
    }

    def prepareUserInfoData(dataOfTrips: List[Array[String]]): List[UserInfo] = {
      dataOfTrips.map(line =>
        UserInfo(
          if (line(ColumnsEnum.genderColumnIndex.id) == "1") GenderEnum.male
          else if (line(ColumnsEnum.genderColumnIndex.id) == "2") GenderEnum.female
          else GenderEnum.unknown,
          if (line(ColumnsEnum.userTypeColumnIndex.id) == UserTypeEnum.Customer.toString) UserTypeEnum.Customer
          else UserTypeEnum.Subscriber,
          line(ColumnsEnum.birthYearColumnIndex.id)))
    }

    def prepareTripData(dataOfTrips: List[Array[String]]): List[Trip] = {
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
