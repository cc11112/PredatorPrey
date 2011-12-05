package edu.luc.edu.cs473.pp

import scala.actors._
import scala.collection.mutable.ListBuffer

object WorldActor extends Actor {
  private val haresPopulation = new ListBuffer[Hare]
  private val lynxPopulation = new ListBuffer[Lynx]

  def act() {
    Actor.loop {
      react {
        case h: Hare => HandleHares(h)
        case l: Lynx => HandleLynx(l)
        case "hares" => println("Hares Population" + haresPopulation.count(e => true).toString())
        case "lynx" =>  println("Lynx Population" + lynxPopulation.count(e => true).toString())
        case _ => exit()
      }
    }
  }

  def HandleHares(hare: Hare) = {

    if (hare.getDying()) {
      //remove from hares populations
      haresPopulation -= hare
    } else {
      //add to hares populations
      hare.start
      haresPopulation += hare
    }
  }

  def HandleLynx(lynx: Lynx) = {

    if (lynx.getDying()) {
      //remove from lynx populations
      lynxPopulation -= lynx
    } else {
      //add to lynx populations
      lynx.start
      lynxPopulation += lynx
    }
  }

  def main(args: Array[String]): Unit = {

    //TODO: configure

    ClockActor.Start()
    
    Thread.sleep(1000 * 10)

    ClockActor.Stop()

  }
}
