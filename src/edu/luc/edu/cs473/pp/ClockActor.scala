package edu.luc.edu.cs473.pp

import scala.actors._

object ClockActor extends Actor {
  private var running: Boolean = false
  def act() {
    Actor.loop {
      react {
        case "stop" => reply(Stop())
        case "ticker" => reply(Ticker())
        case x: Any => println("Error: clock message! " + x)
      }
    }
  }

  /**
   * start clock
   */
  def Start() = {
    println("timer start...")

    running = true

    ClockActor.start()

    ClockActor ! "ticker"
  }

  /**
   * stop clock
   */
  def Stop(): String = {

    println("timer stop.")
    
    WorldActor ! "exit"
    
    running = false
    
    ClockActor.exit()
    
    "stop"
  }

  /**
   * every ticker to send world ticker message
   */
  private def Ticker(): String = {

    if (running) {
      println(System.nanoTime.toString() + ": ")

      WorldActor ! "ticker"

      Thread.sleep(1000)

      ClockActor ! "ticker"
    }
    "ticker"
  }
}