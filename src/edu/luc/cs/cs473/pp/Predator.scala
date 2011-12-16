package edu.luc.cs.cs473.pp

import scala.actors._

case class Hare(
  age: Int, 				//initial age
  maxLifeSpan: Int, 		//max-hare-age
  hareBirthRate: Double, 	//hare-birth-rate
  hareReproduce: Int, 		//every time reproduce 
  startX: Int, startY: Int, //initial hare position: X, Y
  hareStep: Int				//every time randomly move step
  )
  extends PredatorPreyAgent(age, maxLifeSpan, startX, startY) {

  def act() {
    Actor.loop {
      react {
        case "alive" => {
          run(hareStep)
          tryToMakeBunnies()
          setAge()
          isDying()
        }
        case "die" => quit()
        case x: Any => displayMessage("Warning! Hare:" + hashCode() + " got message:" + x)
      }
    }
  }

  /**
   * Try to make Bunnies
   */
  private def tryToMakeBunnies() = {
    if (canReproduce(hareBirthRate)) {
      //send world message to generate a new bunnies
      for (i <- (0 to getReproduceNumber()))
        WorldActor ! new Hare(0, maxLifeSpan, hareBirthRate, hareReproduce, getX(), getY(), hareStep)
    }
  }

  /**
   * mate Probability
   */
  private def getReproduceNumber(): Int = (math.random * hareReproduce).toInt

  /**
   * is dying coming?
   */

  override def isDying() = {
    if (getAge() > maxLifeSpan) {
      quit()
    }
  }

  /**
   * send notice to world
   */
  override def die() = WorldActor ! this
}
