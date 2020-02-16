package by.itechart.internship.entities

import slick.jdbc.PostgresProfile.api._

class StationTable(tag: Tag) extends Table[Station](tag, "station") {
  def station_id = column[Long]("station_id", O.PrimaryKey)

  def name_station = column[String]("name_station")

  def latitude = column[Double]("latitude")

  def longitude = column[Double]("longitude")

  def * = (station_id, name_station, latitude, longitude).mapTo[Station]
}
