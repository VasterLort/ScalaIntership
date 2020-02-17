package by.itechart.internship.entities

import slick.jdbc.PostgresProfile.api._

class TripTable(tag: Tag) extends Table[Trip](tag, "trip") {
  def trip_id = column[Long]("trip_id", O.PrimaryKey)

  def trip_duration = column[Long]("trip_duration")

  def station_start_id = column[Long]("station_start_id")

  def station_end_id = column[Long]("station_end_id")

  def user_id = column[Long]("user_id")

  def bike_id = column[Long]("bike_id")

  def * = (trip_id, trip_duration, station_start_id, station_end_id, user_id, bike_id).mapTo[Trip]
}
