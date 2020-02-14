package by.itechart.internship.config

import com.typesafe.config.ConfigFactory
import org.flywaydb.core.Flyway

object FlywayConfig {
  private lazy val configValues = ConfigFactory.load("flyway.conf")

  def ex(): Unit = {
    val flyway = new Flyway
    flyway.setDataSource(
      configValues.getString("database.url"),
      configValues.getString("database.username"),
      configValues.getString("database.password"))

    flyway.migrate()
  }
}
