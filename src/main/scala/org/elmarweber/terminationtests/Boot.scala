package org.elmarweber.terminationtests

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

import scala.concurrent.{Await, TimeoutException}
import scala.concurrent.duration._

object Boot extends App {
  implicit val system = ActorSystem("test")
  implicit val mat = ActorMaterializer()


  system.registerOnTermination(() => {
    println("Shutdown from actor system")
  })

  sys.addShutdownHook(() => {
    println("Shutdown from sys hook")
    println("Triggering shutdown of actor system with 10s timeout")
    try {
      Await.result(system.terminate(), 10.seconds)
      println("Finished clean shut down")
    } catch {
      case ex: TimeoutException =>
        println("ERROR: Timed out waiting for actor system shutdown")
    }
  })

}
