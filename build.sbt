
name := "aws-secretsmanager"
version := "0.1"
scalaVersion := "2.13.4

crossScalaVersions := Seq("2.13.4", "2.12.12")

libraryDependencies ++= Seq(
  "io.monix" %% "monix" % "3.3.0",
  "com.amazonaws" % "aws-java-sdk" % "1.11.927",

  "org.scalatest" %% "scalatest" % "latest.integration" % "test"
)