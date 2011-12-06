package edu.luc.edu.cs473.pp

import scala.actors._

object ClockActor extends Actor {
  def act() {
    Actor.loop {
      react {
        case "ticker" => Ticker()
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
  def Stop() = {
    ClockActor ! None
    WorldActor ! None
    println("stop")
  }

  /**
   * every ticker to send world ticker message
   */
  def Ticker() = {
    //println("runing...")
    println(System.nanoTime.toString() + ": ")

    WorldActor ! "ticker"

    Thread.sleep(1000)

    ClockActor ! "ticker"
  }
}