package by.itechart.internship.dao

import by.itechart.internship.config.DatabaseConfig
import by.itechart.internship.config.MyPostgresDriver.api._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

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

class TripDAO(val dbProvider: DatabaseConfig.type = DatabaseConfig) {
  val db = dbProvider.db
  private val trips = TableQuery[TripTable]

  def insert(trip: Trip): Future[Unit] = db.run(trips += trip).map { _ => () }

  def insert(listOfTrips: List[Trip]): Future[Unit] = db.run(trips ++= listOfTrips).map { _ => () }

  def deleteAll(): Future[Unit] = db.run(trips.delete).map { _ => () }

  private class TripTable(tag: Tag) extends Table[Trip](tag, "trip") {
    def tripId = column[Long]("trip_id", O.PrimaryKey, O.AutoInc)

    def tripDuration = column[Long]("trip_duration")

    def stationStartId = column[Long]("station_start_id")

    def stationEndId = column[Long]("station_end_id")

    def userId = column[Long]("user_id", O.AutoInc)

    def bikeId = column[Long]("bike_id")

    def startTime = column[String]("start_time")

    def endTime = column[String]("end_time")

    def * = (tripDuration, stationStartId, stationEndId, bikeId, startTime, endTime, userId, tripId) <> (Trip.tupled, Trip.unapply)
  }

}

