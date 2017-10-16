package org.elmarweber.terminationtests

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

import scala.concurrent.{Await, TimeoutException}
import scala.concurrent.duration._

object Boot extends App {
  implicit val system = ActorSystem("test")
  implicit val mat = ActorMaterializer()

  val sleepSysHook = sys.env.getOrElse("SKATT_SLEEP_SYS_HOOK", "0").toLong

  system.registerOnTermination(() => {
    println("Shutdown from actor system")
  })

  sys.addShutdownHook(() => {
    println("Shutdown from sys hook")
    println(s"Sleeping for ${sleepSysHook}ms as per config")
    Thread.sleep(sleepSysHook)
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
