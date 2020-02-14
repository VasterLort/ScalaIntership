package by.itechart.internship.entity

import slick.jdbc.PostgresProfile.api._

class StationTable(tag: Tag) extends Table[Station](tag, "user_info") {
  def id_station = column[Long]("id_station", O.PrimaryKey, O.AutoInc)

  def name_station = column[String]("name_station")

  def latitude = column[Double]("latitude")

  def longitude = column[Double]("longitude")

  def * = (id_station, name_station, latitude, longitude).mapTo[Station]
}
