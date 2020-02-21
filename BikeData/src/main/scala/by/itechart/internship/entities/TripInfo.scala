package by.itechart.internship.entities

import by.itechart.internship.logic.MyPostgresDriver.api._
import by.itechart.internship.types.GenderEnum.Gender
import by.itechart.internship.types.UserTypeEnum.UserType

case class TripInfo(
                     trip_id: Long,
                     trip_duration: Long,
                     station_start_id: Long,
                     station_end_id: Long,
                     user_type: UserType,
                     gender: Gender,
                     yearOfBirth: String,
                     bike_id: Long,
                     start_time: String,
                     end_time: String
                   )

class TripInfoView(tag: Tag) extends Table[TripInfo](tag, "trip_view") {
  def tripId = column[Long]("trip_id")

  def tripDuration = column[Long]("trip_duration")

  def stationStartId = column[Long]("station_start_id")

  def stationEndId = column[Long]("station_end_id")

  def userType = column[UserType]("user_type")

  def gender = column[Gender]("gender")

  def yearOfBirth = column[String]("year_of_birth")

  def bikeId = column[Long]("bike_id")

  def startTime = column[String]("start_time")

  def endTime = column[String]("end_time")

  def * = (tripId, tripDuration, stationStartId, stationEndId,
    userType, gender, yearOfBirth, bikeId, startTime, endTime).mapTo[TripInfo]
}
