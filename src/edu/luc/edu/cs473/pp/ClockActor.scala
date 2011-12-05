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

  /*
   * every ticker to print hares & lynx Population
   */
  def Ticker() = {
    //println("runing...")
    println(System.nanoTime.toString() + ": " ) 

    WorldActor ! "hares" 
    WorldActor ! "lynx"
    
    Thread.sleep(1000)
    ClockActor ! "ticker"
  }
}