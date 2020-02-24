package by.itechart.internship.service

import by.itechart.internship.dao.{TripInfo, TripInfoDao}
import by.itechart.internship.types.Daos

import scala.concurrent.Future

class DataGettingService(tripInfoDAO: TripInfoDao = Daos.tripInfoDao) {
  def getTripInfo(): Future[List[TripInfo]] = {
    val actionResult = tripInfoDAO.getAll()
    actionResult
  }
}
