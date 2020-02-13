package by.itechart.internship

import com.typesafe.config.ConfigFactory

object LightBendConfig {
  private lazy val configLoader = ConfigFactory.load("lightbend.conf")
  val configSetter = ConfigValues(configLoader.getString("url.pathFilesStats"),
    configLoader.getString("url.pathFileTripData"), configLoader.getString("url.pathFileGeneralStats"),
    configLoader.getString("url.pathFileUsageStats"), configLoader.getString("url.pathFileBikeStats"),
    configLoader.getInt("fileCSV.nameColumnsIndex"), configLoader.getString("fileCSV.genderMenValue"),
    configLoader.getString("fileCSV.genderWomenValue"), configLoader.getString("fileCSV.delimiterOfFile"))
}
