package edu.luc.edu.cs473.pp

import scala.actors._
import scala.collection.mutable.ListBuffer

object WorldActor extends Actor {
  private val haresPopulation = new ListBuffer[Hare]
  private val lynxPopulation = new ListBuffer[Lynx]

  def act() {
    Actor.loop {
      react {
        case h: Hare => handleHares(h)
        case l: Lynx => handleLynx(l)
        case "ticker" => simlulate()
        case "output" => output()
        case ("whereishare", l: Lynx) => searchHaresForLynx(l)
        case _ => exit()
      }
    }
  }

  /*
   * to handle hare instance: new/destory
   */
  def handleHares(hare: Hare) = {

    if (hare.getDying()) {
      //remove from hares populations
      haresPopulation -= hare
    } else {
      //add to hares populations
      hare.start
      haresPopulation += hare
    }
  }

  /*
   * to handle lynx instance: new/destory
   */
  def handleLynx(lynx: Lynx) = {

    if (lynx.getDying()) {
      //remove from lynx populations
      lynxPopulation -= lynx
    } else {
      //add to lynx populations
      lynx.start
      lynxPopulation += lynx
    }
  }

  /*
   * search hares for lynx
   */
  def searchHaresForLynx(lynx: Lynx) = {
    //TODO: search the first hare for lynx
    val hare = haresPopulation.find(e => e.isOnThisPot(lynx.getX(), lynx.getY()))
    if (!hare.isEmpty) {
      haresPopulation -= hare.get
      lynx ! hare
    }
  }

  /*
   * Ask Hares/Lynx every ticker
   */
  def simlulate() = {
    for (hare <- haresPopulation) {
      hare ! "run"
      hare ! "alive"
      hare ! "die"
    }

    for (lynx <- lynxPopulation) {
      lynx ! "run"
      lynx ! "alive"
      lynx ! "die"
    }
  }

  def output() = {
    println("Hares Population" + haresPopulation.count(e => true).toString())
    println("Lynx Population" + lynxPopulation.count(e => true).toString())
  }

  def main(args: Array[String]): Unit = {

    //TODO: configure

    ClockActor.Start()

    Thread.sleep(1000 * 10)

    ClockActor.Stop()

  }
}
