package by.itechart.internship.entities

import slick.jdbc.PostgresProfile.api._

case class Trip(
                 tripDuration: Long,
                 stationStartId: Long,
                 stationEndId: Long,
                 bikeId: Long,
                 startTime: String,
                 endTime: String,
                 userId: Long = 0L,
                 tripId: Long = 0L
               )



class TripTable(tag: Tag) extends Table[Trip](tag, "trip") {
  def tripId = column[Long]("trip_id", O.PrimaryKey, O.AutoInc)

  def tripDuration = column[Long]("trip_duration")

  def stationStartId = column[Long]("station_start_id")

  def stationEndId = column[Long]("station_end_id")

  def userId = column[Long]("user_id", O.AutoInc)

  def bikeId = column[Long]("bike_id")

  def startTime = column[String]("start_time")

  def endTime = column[String]("end_time")

  def * = (tripDuration, stationStartId, stationEndId, bikeId, startTime, endTime, userId, tripId).mapTo[Trip]
}