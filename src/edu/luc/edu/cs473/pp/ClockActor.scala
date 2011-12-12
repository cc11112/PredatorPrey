package edu.luc.edu.cs473.pp

import scala.actors._

object ClockActor extends Actor {
  private var running: Boolean = false
  def act() {
    Actor.loop {
      react {
        case "stop" => reply(Stop())
        case "ticker" => reply(Ticker())
        case "exit" => exit()
      }
    }
  }

  /**
   * start clock
   */
  def Start() = {
    println("timer start...")

    running = true

    ClockActor.start

    ClockActor ! "ticker"
  }

  /**
   * stop clock
   */
  private def Stop(): String = {

    WorldActor !? "exit"

    running = false

    ClockActor.exit()

    println("timer stop.")
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