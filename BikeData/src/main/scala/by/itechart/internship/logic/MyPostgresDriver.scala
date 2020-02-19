package by.itechart.internship.logic

import by.itechart.internship.types.{GenderEnum, UserTypeEnum}
import com.github.tminglei.slickpg.PgEnumSupport
import slick.jdbc.PostgresProfile

object MyPostgresDriver extends PostgresProfile with PgEnumSupport {
  override val api = new API with MyEnumImplicits {}

  trait MyEnumImplicits {
    implicit val genderTypeMapper = createEnumJdbcType("Gender", GenderEnum)
    implicit val genderListTypeMapper = createEnumListJdbcType("Gender", GenderEnum)
    implicit val genderColumnExtensionMethodsBuilder = createEnumColumnExtensionMethodsBuilder(GenderEnum)
    implicit val genderOptionColumnExtensionMethodsBuilder = createEnumOptionColumnExtensionMethodsBuilder(GenderEnum)

    implicit val userTypeMapper = createEnumJdbcType("UserType", UserTypeEnum)
    implicit val userTypeListTypeMapper = createEnumListJdbcType("UserType", UserTypeEnum)
    implicit val userTypeColumnExtensionMethodsBuilder = createEnumOptionColumnExtensionMethodsBuilder(UserTypeEnum)
    implicit val userOptionColumnExtensionMethodsBuilder = createEnumOptionColumnExtensionMethodsBuilder(UserTypeEnum)
  }
}
