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

class BikeDao(val dbProvider: DatabaseConfig.type = DatabaseConfig) {
  val db = dbProvider.db
  private val bikes = TableQuery[BikeTable]

  def insert(listOfBikes: List[Bike]): Future[Int] = db.run(bikes ++= listOfBikes).map(_.size)

  def deleteAll(): Future[Int] = db.run(bikes.delete)

  private class BikeTable(tag: Tag) extends Table[Bike](tag, "bike") {
    def bikeId = column[Long]("bike_id", O.PrimaryKey)

    def dateOfAppearance = column[String]("date_of_appearance")

    def lastUsage = column[String]("last_usage")

    def * = (bikeId, dateOfAppearance, lastUsage) <> (Bike.tupled, Bike.unapply)
  }

}

