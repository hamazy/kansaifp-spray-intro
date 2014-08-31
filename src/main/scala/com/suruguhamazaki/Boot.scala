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

class MyService extends HttpServiceActor with ActorLogging {

  def receive: Receive = runRoute(route).orElse {
    case _: Http.Bound ⇒
  }
  def route: Route =
    complete("Hello")
}
