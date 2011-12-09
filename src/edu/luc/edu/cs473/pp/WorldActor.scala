package edu.luc.edu.cs473.pp

import scala.actors._
import scala.collection.mutable.ListBuffer
import scala.concurrent.ops._

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
      haresPopulation += hare
      hare.start()
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
      lynxPopulation += lynx
      lynx.start()
    }
  }

  /**
   * search hares for lynx
   */
  def searchHaresForLynx(lynx: Lynx) = {
    spawn({
    	val hare = haresPopulation.find(e => e.isOnThisPot(lynx.getX(), lynx.getY()))

	    if (!hare.isEmpty && !lynx.getDying()) {
	      displayMessage("lynx" + lynx.hashCode() + " is catching a hare: " + hare.hashCode())
	      haresPopulation -= hare.get
	      lynx ! hare.get
	    }
    })
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

    val h: Int = getHaresCount()
    var l: Int = getLynxCount()
    
    println("====Hares Population: " + h.toString())
    println("====Lynx Population: " + l.toString())

    if (h == 0 && l == 0) {
      ClockActor ! "stop"
      System.exit(0)
    }
  }

  /**
   * get total hares number, not including dying hares
   */
  def getHaresCount(): Int = haresPopulation.count(e => !e.getDying())

  /**
   * get total lynx number, not including dying lynx
   */
  def getLynxCount(): Int = lynxPopulation.count(e => !e.getDying())

  def displayMessage(s: String) = {
    if (Configure.DisplayMessage)
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

    ClockActor ! "stop"

    System.exit(0)
  }
}
