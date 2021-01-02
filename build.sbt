
name := "aws-secretsmanager"
scalaVersion := "2.13.4"
crossScalaVersions := Seq("2.13.4", "2.12.12")
crossPaths := false

organization := "io.github.eduardohl"
homepage := Some(url("https://github.com/eduardohl/aws-secretsmanager"))
scmInfo := Some(ScmInfo(url("https://github.com/eduardohl/aws-secretsmanager"), "git@github.com:eduardohl/aws-secretsmanager.git"))
licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0"))
publishMavenStyle := true

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

publishTo := Some(
  if (isSnapshot.value)
    Opts.resolver.sonatypeSnapshots
  else
    Opts.resolver.sonatypeStaging
)