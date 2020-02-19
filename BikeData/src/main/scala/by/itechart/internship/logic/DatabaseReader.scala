package by.itechart.internship.logic

import by.itechart.internship.entities.{Trip, TripTable}
import by.itechart.internship.logic.MyPostgresDriver.api._

import scala.concurrent._
import ExecutionContext.Implicits.global

object DatabaseReader {
  def writer(): Future[List[Trip]] = {
    val db = Database.forConfig("database")
    val selectAction = TableQuery[TripTable]
    db.run(selectAction.result).map(_.map(_.asInstanceOf[Trip]).toList)
  }
}
