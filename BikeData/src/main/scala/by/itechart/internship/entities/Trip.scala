package by.itechart.internship.entities

case class Trip(
                 trip_id: Long = 0L,
                 trip_duration: Long,
                 station_start_id: Long,
                 station_end_id: Long,
                 user_id: Long,
                 bike_id: Long
               )
