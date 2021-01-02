
name := "aws-secretsmanager"
version := "0.1"
scalaVersion := "2.13.4"

crossScalaVersions := Seq("2.13.4", "2.12.12")

val testcontainersVersion = "0.38.8"

libraryDependencies ++= Seq(
  "io.monix" %% "monix" % "3.3.0",
  "com.amazonaws" % "aws-java-sdk" % "1.11.927",
  "org.json4s" %% "json4s-native" % "3.6.10",

  "com.dimafeng" %% "testcontainers-scala-scalatest" % testcontainersVersion % Test,
  "com.dimafeng" %% "testcontainers-scala-localstack" % testcontainersVersion % Test,
  "org.scalatest" %% "scalatest" % "latest.integration" % Test
)

Test / fork := true