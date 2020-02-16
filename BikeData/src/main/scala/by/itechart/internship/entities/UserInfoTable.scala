package by.itechart.internship.entities

import slick.jdbc.PostgresProfile.api._

class UserInfoTable(tag: Tag) extends Table[UserInfo](tag, "user_info") {
  def user_id = column[Long]("user_id", O.PrimaryKey, O.AutoInc)

  def gender_id = column[Long]("gender_id")

  def user_type_id = column[Long]("user_type_id")

  def year_of_birth = column[Int]("year_of_birth")

  def * = (user_id, gender_id, user_type_id, year_of_birth).mapTo[UserInfo]
}
