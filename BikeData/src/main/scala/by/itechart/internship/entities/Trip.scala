package by.itechart.internship.entities

import slick.jdbc.PostgresProfile.api._

case class Trip(
                 trip_id: Long,
                 trip_duration: Long,
                 station_start_id: Long,
                 station_end_id: Long,
                 user_id: Long,
                 bike_id: Long
               )

class TripTable(tag: Tag) extends Table[Trip](tag, "trip") {
  def tripId = column[Long]("trip_id", O.PrimaryKey)

  def tripDuration = column[Long]("trip_duration")

  def stationStartId = column[Long]("station_start_id")

  def stationEndId = column[Long]("station_end_id")

  def userId = column[Long]("user_id")

  def bikeId = column[Long]("bike_id")

  def * = (tripId, tripDuration, stationStartId, stationEndId, userId, bikeId).mapTo[Trip]
}