package com.suruguhamazaki

import akka.actor._
import akka.io.IO
import spray.can.Http
import spray.http._
import spray.routing._

object Boot extends App {

  implicit val system = ActorSystem("on-spray-can")

  system.actorOf(Props(new Actor with ActorLogging {

    IO(Http) ! Http.Bind(self, interface = "localhost", port = 8080)

    def receive: Receive = {
      case _: Http.Bound ⇒ log.info(s"${self.path.name} started.")
      case Http.Connected(remote, local) ⇒
        log.info(s"Connected from $remote to $local")
        sender ! Http.Register(self)
      case req: HttpRequest ⇒
        log.info(s"Request received: $req")
        sender ! HttpResponse(entity = "Hello")
      case _: Http.ConnectionClosed ⇒
        log.info("Connetcion closed.")
    }
  }))

}
