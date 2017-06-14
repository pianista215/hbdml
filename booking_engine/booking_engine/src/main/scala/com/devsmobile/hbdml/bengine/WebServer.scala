import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory

import scala.io.StdIn
import akka.http.scaladsl.server.Directives
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.devsmobile.hbdml.bengine.{BookingConfirmation, BookingRequest, BookingServiceImpl}
import spray.json._

// collect your json format instances into a support trait:
trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val bookingRequestFormat = jsonFormat3(BookingRequest)
  implicit val bookingConfFormat = jsonFormat3(BookingConfirmation)
}

class BookingService extends Directives with JsonSupport {
  val route =
    post {
      path("book") {
        entity(as[BookingRequest]) { booking =>
          complete(200 -> BookingServiceImpl.bookHotel(booking))
        }
      }
    }
}

object WebServer {
  def main(args: Array[String]) {

    implicit val system = ActorSystem("my-system")
    implicit val materializer = ActorMaterializer()
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext = system.dispatcher

    val config = ConfigFactory.load()

    val host = config.getString("webapp.host")
    val port = config.getInt("webapp.port")

    val service = new BookingService()

    val bindingFuture = Http().bindAndHandle(service.route, host, port)

    println(s"Server online at http://${host}:${port}/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }
}