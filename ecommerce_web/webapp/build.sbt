name := "webapp"
version := "1.0"
scalaVersion := "2.12.2"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % "10.0.6",
  "com.typesafe" % "config" % "1.3.1",
  "com.typesafe.akka" %% "akka-http-testkit" % "10.0.6" % Test
)

enablePlugins(JavaAppPackaging) //TODO: To server

maintainer in Docker := "Unai Sarasola <unaisarasolaalvarez at gmail dot com>"
packageSummary in Docker := "Mock hotel web for HBDML"
packageDescription := "Docker that envolves an Akka-http application which mock an Hotels B2C"
packageName in Docker := "devsmobile/hotels_webapp"
dockerExposedPorts in Docker := Seq(8080)
    