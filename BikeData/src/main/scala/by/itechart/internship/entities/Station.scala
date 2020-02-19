package by.itechart.internship.entities

import slick.jdbc.PostgresProfile.api._

case class Station(
                    stationId: Long,
                    nameStation: String,
                    latitude: Double,
                    longitude: Double
                  )

class StationTable(tag: Tag) extends Table[Station](tag, "station") {
  def stationId = column[Long]("station_id", O.PrimaryKey)

  def nameStation = column[String]("name_station")

  def latitude = column[Double]("latitude")

  def longitude = column[Double]("longitude")

  def * = (stationId, nameStation, latitude, longitude).mapTo[Station]
}
