package by.itechart.internship.dao

import by.itechart.internship.config.DatabaseConfig
import by.itechart.internship.config.MyPostgresDriver.api._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

case class Bike(
                 bikeId: Long,
                 dateOfAppearance: String,
                 lastUsage: String
               )

class BikeDAO(val dbProvider: DatabaseConfig.type = DatabaseConfig) {
  val db = dbProvider.db
  private val bikes = TableQuery[BikeTable]

  def insert(bike: Bike): Future[Unit] = db.run(bikes += bike).map { _ => () }

  def insert(listOfBikes: List[Bike]): Future[Unit] = db.run(bikes ++= listOfBikes).map { _ => () }

  def deleteAll(): Future[Unit] = db.run(bikes.delete).map { _ => () }

  private class BikeTable(tag: Tag) extends Table[Bike](tag, "bike") {
    def bikeId = column[Long]("bike_id", O.PrimaryKey)

    def dateOfAppearance = column[String]("date_of_appearance")

    def lastUsage = column[String]("last_usage")

    def * = (bikeId, dateOfAppearance, lastUsage) <> (Bike.tupled, Bike.unapply)
  }

}

