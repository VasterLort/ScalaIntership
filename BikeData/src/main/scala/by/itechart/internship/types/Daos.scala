package by.itechart.internship.types

import by.itechart.internship.dao._

object Daos {
  lazy val bikeDao: BikeDao = new BikeDao()
  lazy val userInfoDao: UserInfoDao = new UserInfoDao()
  lazy val tripDao: TripDao = new TripDao()
  lazy val stationDao: StationDao = new StationDao()
  lazy val tripInfoDao: TripInfoDao = new TripInfoDao()
}
