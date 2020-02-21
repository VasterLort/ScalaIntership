package by.itechart.internship.dao

import by.itechart.internship.entities.{TripInfo, TripInfoView}
import by.itechart.internship.logic.MyPostgresDriver.api._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._

object DatabaseReader {
  def writer(): Future[List[TripInfo]] = {
    val db = Database.forConfig("database")
    val selectAction = TableQuery[TripInfoView]
    db.run(selectAction.result).map(_.map(_.asInstanceOf[TripInfo]).toList)
  }
}
