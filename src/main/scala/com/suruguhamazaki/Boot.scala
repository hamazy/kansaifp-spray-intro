package com.suruguhamazaki

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import spray.can.Http

object Boot extends App {

  implicit val system = ActorSystem("on-spray-can")

  val service = system.actorOf(Props[MyServiceActor], "demo-service")

  IO(Http) ! Http.Bind(service, interface = "localhost", port = 8080)
}
