package by.itechart.internship.entities

import by.itechart.internship.logic.Gender
import by.itechart.internship.logic.Gender.Gender
import slick.jdbc.PostgresProfile.api._

class UserInfoTable(tag: Tag) extends Table[UserInfo](tag, "user_info") {
  implicit val GenderMapper = MappedColumnType.base[Gender, String](
    e => e.toString,
    s => Gender.withName(s)
  )

  def user_id = column[Long]("user_id", O.PrimaryKey, O.AutoInc)

  def gender = column[Gender]("gender")

  def user_type = column[String]("user_type")

  def year_of_birth = column[String]("year_of_birth")

  def * = (gender, user_type, year_of_birth, user_id).mapTo[UserInfo]
}
