name := "BikeData"

version := "0.1"

scalaVersion := "2.11.12"

libraryDependencies += "au.com.bytecode" % "opencsv" % "2.4"

libraryDependencies += "com.typesafe" % "config" % "1.4.0"

libraryDependencies ++= Seq(
  "org.slf4s" %% "slf4s-api" % "1.7.12",
  "ch.qos.logback" % "logback-classic" % "1.1.2"
)