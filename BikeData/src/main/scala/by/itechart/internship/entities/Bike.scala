package by.itechart.internship.entities

import java.time.LocalDate


case class Bike(
                 bike_id: Long = 0L,
                 date_of_appearance: LocalDate,
                 last_usage: LocalDate
               )
