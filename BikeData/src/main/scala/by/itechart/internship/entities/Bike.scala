package by.itechart.internship.entities

import by.itechart.internship.logic.MyPostgresDriver.api._

case class Bike(
                 bikeId: Long,
                 dateOfAppearance: String,
                 lastUsage: String
               )

class BikeTable(tag: Tag) extends Table[Bike](tag, "bike") {
  def bikeId = column[Long]("bike_id", O.PrimaryKey)

  def dateOfAppearance = column[String]("date_of_appearance")

  def lastUsage = column[String]("last_usage")

  def * = (bikeId, dateOfAppearance, lastUsage).mapTo[Bike]
}