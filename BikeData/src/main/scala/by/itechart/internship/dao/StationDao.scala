package by.itechart.internship.dao

import by.itechart.internship.config.DatabaseConfig
import by.itechart.internship.config.MyPostgresDriver.api._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

case class Station(
                    stationId: Long,
                    nameStation: String,
                    latitude: Double,
                    longitude: Double
                  )


class StationDao(val dbProvider: DatabaseConfig.type = DatabaseConfig) {
  val db = dbProvider.db
  private val stations = TableQuery[StationTable]

  def insert(listOfStations: List[Station]): Future[Int] = db.run(stations ++= listOfStations).map(_.size)

  def deleteAll(): Future[Int] = db.run(stations.delete)

  private class StationTable(tag: Tag) extends Table[Station](tag, "station") {
    def stationId = column[Long]("station_id", O.PrimaryKey)

    def nameStation = column[String]("name_station")

    def latitude = column[Double]("latitude")

    def longitude = column[Double]("longitude")

    def * = (stationId, nameStation, latitude, longitude) <> (Station.tupled, Station.unapply)
  }

}

