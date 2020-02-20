package by.itechart.internship.logic

import by.itechart.internship.entities.{Trip, TripInfo, TripInfoView, TripTable}
import by.itechart.internship.logic.MyPostgresDriver.api._

import scala.concurrent._
import ExecutionContext.Implicits.global

object DatabaseReader {
  def writer(): Future[List[TripInfo]] = {
    val db = Database.forConfig("database")
    val selectAction = TableQuery[TripInfoView]
    db.run(selectAction.result).map(_.map(_.asInstanceOf[TripInfo]).toList)
  }
}
