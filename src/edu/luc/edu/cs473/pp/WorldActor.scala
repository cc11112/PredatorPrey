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
        case ("whereishare", l: Lynx) => searchHaresForLynx(l)
        case _ => exit()
      }
    }
  }

  /**
   * to handle hare instance: new/destroy
   */
  def handleHares(hare: Hare) = {

    if (hare.getDying()) {
      //remove from hares populations
      displayMessage("hare: " + hare.hashCode() + " dead.")
      haresPopulation -= hare
    } else {
      displayMessage("hare: " + hare.hashCode() + " bron.")
      //add to hares populations
      hare.start
      haresPopulation += hare
    }
  }

  /**
   * to handle lynx instance: new/destroy
   */
  def handleLynx(lynx: Lynx) = {

    if (lynx.getDying()) {
      //remove from lynx populations
      displayMessage("lynx: " + lynx.hashCode() + " dead.")
      lynxPopulation -= lynx
    } else {
      //add to lynx populations
      displayMessage("lynx: " + lynx.hashCode() + " bron.")
      lynx.start
      lynxPopulation += lynx
    }
  }

  /**
   * search hares for lynx
   */
  def searchHaresForLynx(lynx: Lynx) = {
    //TODO: search the first hare for lynx
    val hare = haresPopulation.find(e => e.isOnThisPot(lynx.getX(), lynx.getY()))
    //.dropWhile(e => e.isOnThisPot(lynx.getX(), lynx.getY()))(0)
    if (!hare.isEmpty && !lynx.getDying()) {
      displayMessage("lynx" + lynx.hashCode() + " is catching a hare: " + hare.hashCode())
      haresPopulation -= hare.get
      lynx ! hare
    }
  }

  /**
   * Ask Hares/Lynx every ticker
   */
  def simlulate() = {
    for (hare <- haresPopulation) {
      hare ! "alive"
    }

    for (lynx <- lynxPopulation) {
      lynx ! "alive"
    }
    
    println("====Hares Population: " + haresPopulation.count(e => !e.getDying()).toString())
    println("====Lynx Population: " + lynxPopulation.count(e => !e.getDying()).toString())
  }
  
  def displayMessage(s:String) = {
    if(Configure.DisplayMessage)
      println(s)
  }
     

  /**
   * initial world actor
   */
  def initial(hares: Int, lynx: Int) = {
    println("initial...")

    //create hares
    for (i <- (1 to hares)) {
      handleHares(new Hare(
        (math.random * Configure.HareMaxAge).toInt,
        Configure.HareMaxAge,
        Configure.HareBirthRate,
        Configure.HareReproduce,
        WorldSpace.getRandomX(),
        WorldSpace.getRandomY()))
    }

    //create lynx
    for (i <- (1 to lynx)) {
      handleLynx(new Lynx(
        (math.random * Configure.LynxMaxAge).toInt,
        Configure.LynxMaxAge,
        Configure.lynxMaxReproduce,
        (math.random * Configure.LynxMaxEnergy).toInt + 1,
        Configure.EnergyGainPreHare,
        Configure.EnergyUseReproduce,
        WorldSpace.getRandomX(),
        WorldSpace.getRandomY()))
    }
    
    //start world actor
    start()
  }

  def main(args: Array[String]): Unit = {

    initial(Configure.InitialHares, Configure.InitialLynx)

    ClockActor.Start()

    Thread.sleep(1000 * Configure.Runtime)

    ClockActor.Stop()

    System.exit(0)
  }
}
