package by.itechart.internship.entities

import slick.jdbc.PostgresProfile.api._

class UserTypeTable(tag: Tag) extends Table[UserType](tag, "user_type") {
  def user_type_id = column[Long]("user_type_id", O.PrimaryKey, O.AutoInc)

  def name_user_type = column[String]("name_user_type")

  def description_name_user_type = column[String]("description_name_user_type")

  def * = (user_type_id, name_user_type, description_name_user_type).mapTo[UserType]
}
