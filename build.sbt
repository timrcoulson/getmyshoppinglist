name := """play"""

version := "1.1-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.5"

resolvers += "JitPack" at "https://jitpack.io"

routesGenerator := InjectedRoutesGenerator

libraryDependencies ++= Seq(
  ws,
  filters,
  guice,
  openId,
  "com.typesafe.play" %% "play-json" % "2.6.0",
  "com.typesafe.play" %% "play-iteratees" % "2.6.1",
  "com.typesafe.play" %% "play-iteratees-reactive-streams" % "2.6.1",
  "com.h2database" % "h2" % "1.4.192",
  "com.typesafe.play" %% "play-slick" % "3.0.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "3.0.0",
  "org.apache.commons" % "commons-lang3" % "3.5",
  "com.github.bmoliveira" %% "snake-yaml" % "v1.18-android",
  "com.github.mifmif" % "generex" % "1.0.1",
  "org.json4s" %% "json4s-native" % "3.4.2",
  "mysql" % "mysql-connector-java" % "5.1.34",
  "com.sparkpost" % "sparkpost-lib" % "0.16.1",
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % "test"
)

javaOptions in Test += "-Dconfig.file=conf/application.test.conf"
