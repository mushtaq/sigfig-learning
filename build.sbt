import Dependencies._

ThisBuild / scalaVersion     := "2.13.6"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

ThisBuild / scalacOptions += "-Xasync"

lazy val root = (project in file("."))
  .settings(
    name                                            := "learning1",
    libraryDependencies += scalaTest                 % Test,
    libraryDependencies += "org.scala-lang.modules" %% "scala-async"   % "1.0.1",
    libraryDependencies += "org.scala-lang"          % "scala-reflect" % scalaVersion.value % Provided
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
