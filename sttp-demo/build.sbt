import Dependencies._

ThisBuild / scalaVersion     := "2.12.8"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "STTP Demo",
    libraryDependencies += scalaTest % Test,
    libraryDependencies += sttp,
    libraryDependencies += sttpCirce,
    libraryDependencies += circeGeneric
  )
