import sbt._
import Keys._

object BuildSettings {
  val buildSettings = Defaults.coreDefaultSettings ++ Seq(
    organization := "",
    version := "1.0-SNAPSHOT",
    scalaVersion := "2.11.1",
    scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature", "-encoding", "utf8"),
    shellPrompt := { state => Project.extract(state).currentProject.id + " > " }
  )
}

object Dependencies {
  val akkaVersion = "2.3.2"
  val sprayVersion = "1.3.1"
  val sprayCan = "io.spray" %% "spray-can" % sprayVersion
  val sprayRouting = "io.spray" %% "spray-routing" % sprayVersion
  val sprayJson = "io.spray" %% "spray-json" % "1.2.6"
  val sprayTest = "io.spray" %% "spray-testkit" % sprayVersion % "test"
  val akkaActor = "com.typesafe.akka" %% "akka-actor" % akkaVersion
  val akkaSlf4j = "com.typesafe.akka"   %%  "akka-slf4j" % akkaVersion
  val akkaTestkit = "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test"
  val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging-slf4j" % "2.1.2"
  val logback = "ch.qos.logback" % "logback-classic" % "1.1.2"
  val scalatest = "org.scalatest" %% "scalatest" % "2.2.0" % "test"
  val pegdown = "org.pegdown" % "pegdown" % "1.4.2" % "test"
  val mockito = "org.mockito" % "mockito-core" % "1.9.5" % "test"

  val commonDeps = Seq(
    sprayCan,
    sprayRouting,
    sprayJson,
    sprayTest,
    akkaActor,
    akkaSlf4j,
    akkaTestkit,
    scalaLogging,
    logback,
    scalatest,
    pegdown,
    mockito
  )
}

object Resolvers {
  val sprayRepo = "spray repo" at "http://repo.spray.io/"

  val resolvers = Seq(sprayRepo)
}

object KansaifpSprayIntroBuild extends Build {

  import BuildSettings.buildSettings
  import Dependencies.commonDeps
  import spray.revolver.RevolverPlugin.Revolver
  import org.scalastyle.sbt.ScalastylePlugin.{Settings => ScalastyleSettings}
  import sbtassembly.Plugin.AssemblyKeys
  import sbtassembly.Plugin.assemblySettings
  import scoverage.ScoverageSbtPlugin.instrumentSettings

  lazy val root = Project("kansaifp-spray-intro",
    file("."),
    settings = buildSettings ++
      instrumentSettings ++
      ScalastyleSettings ++
      assemblySettings ++
      Revolver.settings) settings (
    libraryDependencies ++= commonDeps,
        resolvers := Resolvers.resolvers,
        mainClass in AssemblyKeys.assembly := Some("com.suruguhamazaki.Boot"),
        testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-h", "target/report", "-u", "target/junitxml", "-o"))
}
