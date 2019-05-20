import sbt._

object Dependencies {
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.5"
  lazy val sttp = "com.softwaremill.sttp" %% "core" % "1.5.17"
  lazy val sttpCirce = "com.softwaremill.sttp" %% "circe" % "1.5.17"
  lazy val circeGeneric = "io.circe" %% "circe-generic" % "0.10.0"
}
