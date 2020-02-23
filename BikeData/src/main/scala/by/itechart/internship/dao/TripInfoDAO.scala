package by.itechart.internship.dao

import by.itechart.internship.config.MyPostgresDriver.api._
import by.itechart.internship.types.GenderEnum.Gender
import by.itechart.internship.types.UserTypeEnum.UserType

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

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

class TripInfoDAO(configNameDB: String) {
  private val db = Database.forConfig(configNameDB)

  private val tripInfo = TableQuery[TripInfoView]

  def getAll(): Future[List[TripInfo]] = db.run(tripInfo.result).map(_.map(_.asInstanceOf[TripInfo]).toList)

  private class TripInfoView(tag: Tag) extends Table[TripInfo](tag, "trip_view") {
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
      userType, gender, yearOfBirth, bikeId, startTime, endTime) <> (TripInfo.tupled, TripInfo.unapply)
  }

}

