name := "booking_engine"
version := "1.0"
scalaVersion := "2.12.2"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % "10.0.6",
  "com.typesafe" % "config" % "1.3.1",
  "org.mongodb.scala" %% "mongo-scala-driver" % "2.1.0",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.3",
  "com.typesafe.akka" %% "akka-http-testkit" % "10.0.6" % Test
)

enablePlugins(JavaAppPackaging) //TODO: To server

maintainer in Docker := "Unai Sarasola <unaisarasolaalvarez at gmail dot com>"
packageSummary in Docker := "Booking Engine"
packageDescription := "Microservice that will be in charge of process the Bookings"
packageName in Docker := "devsmobile/hotels_booking_engine"
dockerExposedPorts in Docker := Seq(8080)
