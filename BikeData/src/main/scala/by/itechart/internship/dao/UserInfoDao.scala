package by.itechart.internship.dao

import by.itechart.internship.config.DatabaseConfig
import by.itechart.internship.config.MyPostgresDriver.api._
import by.itechart.internship.types.enums.GenderEnum.Gender
import by.itechart.internship.types.enums.UserTypeEnum.UserType

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

case class UserInfo(
                     gender: Gender,
                     userType: UserType,
                     yearOfBirth: String,
                     userId: Long = 0L
                   )

class UserInfoDao(val dbProvider: DatabaseConfig.type = DatabaseConfig) {
  val db = dbProvider.db
  private val usersInfo = TableQuery[UserInfoTable]

  def insert(userInfo: UserInfo): Future[Unit] = db.run(usersInfo += userInfo).map { _ => () }

  def insert(listOfUsersInfo: List[UserInfo]): Future[Unit] = db.run(usersInfo ++= listOfUsersInfo).map { _ => () }

  def deleteAll(): Future[Unit] = db.run(usersInfo.delete).map { _ => () }

  private class UserInfoTable(tag: Tag) extends Table[UserInfo](tag, "user_info") {

    def userId = column[Long]("user_id", O.PrimaryKey, O.AutoInc)

    def gender = column[Gender]("gender")

    def userType = column[UserType]("user_type")

    def yearOfBirth = column[String]("year_of_birth")

    def * = (gender, userType, yearOfBirth, userId) <> (UserInfo.tupled, UserInfo.unapply)
  }

}


