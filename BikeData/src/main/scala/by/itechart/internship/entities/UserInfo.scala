package by.itechart.internship.entities

import by.itechart.internship.logic.Gender.Gender

case class UserInfo(
                     gender: Gender,
                     user_type: String,
                     year_of_birth: String,
                     user_id: Long = 0L
                   )