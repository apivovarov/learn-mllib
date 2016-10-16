lazy val appSettings = Seq(
  organization := "org.x4444",
  name := "learn-mllib",
  version := "1.0.0-SNAPSHOT"
)

// Those settings should be the same as in alchemy!
lazy val scalaVersion_ = "2.10.5" // should be the same as spark
lazy val javaVersion = "1.7" // should be the same as spark

lazy val scalaTestVersion = "2.2.5"
lazy val sparkVersion = "1.6.2"

scalaVersion in Global := scalaVersion_

scalacOptions in Global ++= Seq(
  "-deprecation",
  "-feature",
  "-target:jvm-" + javaVersion,
  "-Xfatal-warnings",
  "-Xlint"
)

javacOptions in Global ++= Seq(
  "-encoding", "UTF-8",
  "-source", javaVersion,
  "-target", javaVersion
)

lazy val sparkLib = Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion % Provided withSources() withJavadoc(),
  "org.apache.spark" %% "spark-mllib" % sparkVersion % Provided withSources() withJavadoc()
)

lazy val auxLib = Seq(
)

lazy val testLib = Seq(
  "org.scalatest" %% "scalatest" % scalaTestVersion % Test withSources() withJavadoc()
)

lazy val root = (project in file("."))
  .settings(appSettings: _*)
  .settings(
    libraryDependencies ++= sparkLib,
    libraryDependencies ++= auxLib,
    libraryDependencies ++= testLib
  )

