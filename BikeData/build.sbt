name := "BikeData"
version := "1.3.8"
scalaVersion := "2.11.12"

libraryDependencies += "au.com.bytecode" % "opencsv" % "2.4"

libraryDependencies += "com.typesafe" % "config" % "1.4.0"

libraryDependencies ++= Seq(
  "org.slf4s" %% "slf4s-api" % "1.7.12",
  "ch.qos.logback" % "logback-classic" % "1.1.2"
)

libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % "3.3.2",
  "org.postgresql" % "postgresql" % "9.3-1100-jdbc41",
  "ch.qos.logback" % "logback-classic" % "1.2.3"
)

libraryDependencies += "org.flywaydb" % "flyway-sbt" % "4.2.0"

libraryDependencies += "com.github.tminglei" %% "slick-pg" % "0.18.1"

enablePlugins(FlywayPlugin)

flywayUrl := "jdbc:postgresql://localhost/data_trip"
flywayUser := "postgres"
flywayPassword := "kanekiken1998"
flywayLocations += "db/migration"