package edu.luc.edu.cs473.pp

import scala.actors._

object ClockActor extends Actor {
  def act() {
    Actor.loop {
      react {
        case "stop" => Stop()
        case "ticker" => reply(Ticker())
        case _ => exit()
      }
    }
  }

  /**
   * start clock
   */
  def Start() = {
    println("start...")
    ClockActor.start
    ClockActor ! "ticker"
  }

  /**
   * stop clock
   */
  private def Stop() = {
    ClockActor ! None
    WorldActor ! None
    println("stop")
  }

  /**
   * every ticker to send world ticker message
   */
  private def Ticker() : String = {
    //println("runing...")
    println(System.nanoTime.toString() + ": ")

    WorldActor ! "ticker"

    Thread.sleep(1000)

    ClockActor ! "ticker"
    
    "ticker"
  }
}