package by.itechart.internship.config

import com.typesafe.config.ConfigFactory

case class LightBendConfig(
                            pathFilesStats: String,
                            pathFileTripData: String,
                            pathFileGeneralStats: String,
                            pathFileUsageStats: String,
                            pathFileBikeStats: String,
                            nameColumnsIndex: Int,
                            genderMenValue: String,
                            genderWomenValue: String,
                            delimiterOfFile: String,
                            databaseUrl: String,
                            databaseUsername: String,
                            databasePassword: String
                          )

object LightBendConfig {
  private lazy val configLoader = ConfigFactory.load("lightbend.conf")
  val configSetter = LightBendConfig(configLoader.getString("url.pathFilesStats"),
    configLoader.getString("url.pathFileTripData"), configLoader.getString("url.pathFileGeneralStats"),
    configLoader.getString("url.pathFileUsageStats"), configLoader.getString("url.pathFileBikeStats"),
    configLoader.getInt("fileCSV.nameColumnsIndex"), configLoader.getString("fileCSV.genderMenValue"),
    configLoader.getString("fileCSV.genderWomenValue"), configLoader.getString("fileCSV.delimiterOfFile"),
    configLoader.getString("database.databaseUrl"), configLoader.getString("database.databaseUsername"),
    configLoader.getString("database.databasePassword"))
}
