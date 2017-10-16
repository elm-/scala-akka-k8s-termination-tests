import com.typesafe.sbt.SbtNativePackager._
import com.typesafe.sbt.packager.SettingsHelper._
import com.typesafe.sbt.packager.debian.DebianPlugin.autoImport.Debian
import com.typesafe.sbt.packager.docker.DockerPlugin.autoImport.Docker

val shortCommit = ("git rev-parse --short HEAD" !!).replaceAll("\\n", "").replaceAll("\\r", "")


lazy val commonSettings = Seq(
  organization := "org.elmarweber",
  version := s"1.0.0-${shortCommit}",
  scalaVersion := "2.11.8",
  resolvers += Resolver.jcenterRepo,
  updateOptions := updateOptions.value.withCachedResolution(true)
)

lazy val serviceSettings = Seq(
  packageName in Docker := "elmarweber/" + name.value,
  dockerBaseImage := "airdock/oracle-jdk:jdk-1.8"
)


val defaultLib = Seq(
  libraryDependencies ++= {
    val akkaV            = "2.5.3"
    val akkaHttpV        = "10.0.9"
    val slf4sV           = "1.7.10"
    val logbackV         = "1.1.3"
    val kamonVersion     = "0.6.7"
    val specs2Version    = "3.8.6"
    val scaffeineVersion = "2.1.0"
    val akkaStreamKafkaV = "0.17"
    Seq(
      "com.typesafe.akka" %% "akka-http"                         % akkaHttpV,
      "com.typesafe.akka" %% "akka-http-spray-json"              % akkaHttpV,
      "com.typesafe.akka" %% "akka-slf4j"                        % akkaV,
      "com.typesafe.akka" %% "akka-stream"                       % akkaV,
      "com.typesafe.akka" %% "akka-actor"                        % akkaV,
      "org.slf4s"         %% "slf4s-api"                         % slf4sV,
      "ch.qos.logback"     % "logback-classic"                   % logbackV
    )
  }
)

lazy val root = (project in file("."))
  .settings(Seq(name := "scala-akka-k8s-termination-tests"))
  .settings(commonSettings)
  .settings(serviceSettings)
  .enablePlugins(JavaServerAppPackaging)
  .enablePlugins(DockerPlugin)
  .settings(defaultLib)


