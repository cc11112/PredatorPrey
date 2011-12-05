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

  def Start() = {
    println("start...")
    ClockActor.start
    ClockActor ! "ticker"
  }

  def Stop() = {
    ClockActor ! None
    println("stop")
  }

  def Ticker() = {
    println("runing...")
    println(System.nanoTime.toString())

    Thread.sleep(1000)
    ClockActor ! "ticker"
  }
}