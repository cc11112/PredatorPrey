package edu.luc.cs.cs473.pp

import scala.actors._

object ClockActor extends Actor {
  private var running: Boolean = false
  def act() {
    Actor.loop {
      react {
        case "stop" => reply(Stop())
        case "ticker" => reply(Ticker())
        case "exit" => exit() 
        case x: Any => println("Warning! clock got message: " + x)
      }
    }
  }

  /**
   * start clock
   */
  def startClock() = {
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
    
    running = false
    
    "stop"
  }

  /**
   * every ticker to send world ticker message
   */
  private def Ticker(): String = {

    if (running) {
      println(System.nanoTime.toString() + ": ")

      WorldActor ! "ticker"

      Thread.sleep(Configure.Ticker)

      ClockActor ! "ticker"
    }
    "ticker"
  }
}