name := """play-java-webservices"""


version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
    javaCore,
    javaJdbc,
    javaJpa,
    jdbc,
    cache,
    javaWs
)

