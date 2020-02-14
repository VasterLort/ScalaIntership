package by.itechart.internship.entity

import slick.jdbc.PostgresProfile.api._

class UserInfoTable(tag: Tag) extends Table[UserInfo](tag, "user_info") {
  def id = column[Long]("id_user", O.PrimaryKey, O.AutoInc)

  def id_gender = column[Long]("id_gender")

  def id_user_type = column[Long]("id_user_type")

  def year_of_birth = column[Int]("year_of_birth")

  def * = (id, id_gender, id_user_type, year_of_birth).mapTo[UserInfo]
}
