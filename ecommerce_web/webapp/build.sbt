name := "webapp"

version := "1.0"

scalaVersion := "2.12.2"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % "10.0.6",
  "com.typesafe" % "config" % "1.3.1",
  "com.typesafe.akka" %% "akka-http-testkit" % "10.0.6" % Test
)
    