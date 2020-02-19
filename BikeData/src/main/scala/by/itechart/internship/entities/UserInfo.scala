package by.itechart.internship.entities


import by.itechart.internship.types.GenderEnum.Gender
import by.itechart.internship.logic.MyPostgresDriver.api._
import by.itechart.internship.types.UserTypeEnum.UserType

case class UserInfo(
                     gender: Gender,
                     userType: UserType,
                     yearOfBirth: String,
                     userId: Long = 0L
                   )

class UserInfoTable(tag: Tag) extends Table[UserInfo](tag, "user_info") {

  def userId = column[Long]("user_id", O.PrimaryKey, O.AutoInc)

  def gender = column[Gender]("gender")

  def userType = column[UserType]("user_type")

  def yearOfBirth = column[String]("year_of_birth")

  def * = (gender, userType, yearOfBirth, userId).mapTo[UserInfo]
}
