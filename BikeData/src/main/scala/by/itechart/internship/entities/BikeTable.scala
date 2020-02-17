package by.itechart.internship.entities

import slick.jdbc.PostgresProfile.api._

class BikeTable(tag: Tag) extends Table[Bike](tag, "bike") {
  def bike_id = column[Long]("bike_id", O.PrimaryKey)

  def date_of_appearance = column[String]("date_of_appearance")

  def last_usage = column[String]("last_usage")

  def * = (bike_id, date_of_appearance, last_usage).mapTo[Bike]
}