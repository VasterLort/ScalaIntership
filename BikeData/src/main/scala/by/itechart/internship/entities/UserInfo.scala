package by.itechart.internship.entities

import by.itechart.internship.logic.Gender
import by.itechart.internship.logic.Gender.Gender
import slick.jdbc.PostgresProfile.api._

case class UserInfo(
                     gender: Gender,
                     user_type: String,
                     year_of_birth: String,
                     user_id: Long = 0L
                   )

class UserInfoTable(tag: Tag) extends Table[UserInfo](tag, "user_info") {
  implicit val GenderMapper = MappedColumnType.base[Gender, String](
    e => e.toString,
    s => Gender.withName(s)
  )

  def userId = column[Long]("user_id", O.PrimaryKey, O.AutoInc)

  def gender = column[Gender]("gender")

  def userType = column[String]("user_type")

  def yearOfBirth = column[String]("year_of_birth")

  def * = (gender, userType, yearOfBirth, userId).mapTo[UserInfo]
}
