package by.itechart.internship.config

import org.flywaydb.core.Flyway

object FlywayConfig {
  def initDatabaseStructure(configValues: LightBendConfig): Unit = {
    val flyway = new Flyway
    flyway.setDataSource(
      configValues.databaseUrl,
      configValues.databaseUsername,
      configValues.databasePassword)
    flyway.migrate()
  }
}
