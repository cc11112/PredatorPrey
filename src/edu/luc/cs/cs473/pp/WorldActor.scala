package edu.luc.cs.cs473.pp

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
        case "exit" => quit()
        case x: Any => displayMessage("Warning! Unknown message! " + x)
      }
    }
  }

  /**
   * to handle hare instance: new/destroy
   */
  private def handleHares(hare: Hare) = {
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
  private def handleLynx(lynx: Lynx) = {
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
  private def searchHaresForLynx(lynx: Lynx): Option[Hare] = {
    val hare = haresPopulation.find(e => e.isOnThisPot(lynx.getX(), lynx.getY(), Configure.HareScopeRadius))
    if (!hare.isEmpty && !lynx.getDying()) {
      haresPopulation -= hare.get
      lynx ! hare.get
    }
    hare
  }

  /**
   * Ask Hares/Lynx every ticker
   */
  private def simlulate() = {
    for (hare <- haresPopulation) {
      hare ! "alive"
    }

    for (lynx <- lynxPopulation) {
      lynx ! "alive"
    }

    outputResult()
  }

  /**
   * output result
   *
   */
  private def outputResult() = {

    val h: Int = getHaresCount()
    val l: Int = getLynxCount()

    println("====Hares Population: " + h.toString())
    println("====Lynx Population: " + l.toString())
    
    WorldGUI.ShapeDrawingActor ! (haresPopulation, lynxPopulation)
  }

  /**
   * get total hares number, not including dying hares
   */
  def getHaresCount(): Int = haresPopulation.count(e => !e.getDying())

  /**
   * get total lynx number, not including dying lynx
   */
  def getLynxCount(): Int = lynxPopulation.count(e => !e.getDying())

  private def displayMessage(s: String) = {
    if (Configure.DisplayMessage)
      println(s)
  }

  /**
   * Reset all variables
   */
  def reset() = {
    haresPopulation.clear()
    lynxPopulation.clear()
  }

  /**
   * initial world actor
   */
  private def initial(hares: Int, lynx: Int) = {
    println("initial...")

    reset()

    //create hares
    for (i <- (1 to hares)) {
      handleHares(new Hare(
        (math.random * Configure.HareMaxAge).toInt,
        Configure.HareMaxAge,
        Configure.HareBirthRate,
        Configure.HareReproduce,
        WorldSpace.getRandomX(),
        WorldSpace.getRandomY(),
        Configure.HareRunStep))
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
        WorldSpace.getRandomY(),
        Configure.LynxRunStep))
    }

    outputResult()

    //start world actor
    start()
  }

  def run(running: Boolean): Unit = {
    if (running) {
      initial(Configure.InitialHares, Configure.InitialLynx)
      ClockActor.startClock()
    } else {
      ClockActor !? "stop"
    }
  }

  private def quit() = {
    exit()
    ClockActor ! "exit"

  }

  def main(args: Array[String]): Unit = {

    run(true)

    Thread.sleep(1000 * Configure.Runtime)

    run(false)

    Thread.sleep(1000)

    println("Restart agan...........................")

    run(true)

    Thread.sleep(1000 * Configure.Runtime)

    run(false)

    System.exit(0)
  }
}
