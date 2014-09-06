package com.suruguhamazaki

import akka.actor._
import akka.io.IO
import spray.can.Http
import spray.http._
import spray.routing._

object Boot extends App {

  implicit val system = ActorSystem("on-spray-can")

  implicit val service = system.actorOf(Props[MyService])

  IO(Http) ! Http.Bind(service, interface = "localhost", port = 8080)
}

case class Color(r: Int, g: Int, b: Int)

class MyService extends HttpServiceActor with ActorLogging {

  def receive: Receive = runRoute(route).orElse {
    case _: Http.Bound ⇒
  }
  def route: Route =
    path("foo") {
      get {
        complete("Receive GET /foo")
      } ~ post {
        complete(StatusCodes.OK)
      }
    } ~ path("bar") {
      get {
        complete(<p style="font-size:xx-large;">Receive GET /bar</p>)
      } ~ post {
        complete(HttpResponse(entity = "Receive POST /bar"))
      }
    } ~ path("color") {
      parameters('r.as[Int], 'g.as[Int], 'b.as[Int]).as(Color) { color ⇒
        complete(
          <p style={
            s"color: rgb(${color.r}, ${color.g}, ${color.b})"
          }>Hello</p>)
      }
    }
}
