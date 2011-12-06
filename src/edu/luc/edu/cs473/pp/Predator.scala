package edu.luc.edu.cs473.pp

import scala.actors._

case class Hare(
  age: Int, //initial age
  maxLifeSpan: Int, //max-hare-age
  hareBirthRate: Double, //hare-birth-rate
  hareReproduce: Int, //every time reprodue 
  startX: Int, startY: Int //initial lynx position
  )
  extends PredatorPreyAgent(age, maxLifeSpan, startX, startY) {

  def act() {
    Actor.loop {
      react {
        case "alive" => {
          run()
          tryToMakeBunnies()
          setAge()
          die()
        }
        case "die" => die()
        case _ => exit()
      }
    }
  }

  /**
   * Try to make Bunnies
   */
  def tryToMakeBunnies() = {
    if (canReproduce(hareBirthRate)) {
      //send world message to generate a new bunnies
      for (i <- (1 to getReproduceNumber()))
        WorldActor ! new Hare(0, maxLifeSpan, hareBirthRate, hareReproduce, getX(), getY())
    }
  }

  /**
   * mate Probability
   */
  def getReproduceNumber(): Int = (math.random * hareReproduce ).toInt

  /**
   * Is this hare at this area
   */
  def isOnThisPot(x: Int, y: Int): Boolean = {
    (x - 10 <= getX() && getX() <= x + 10
      && y - 10 <= getY() && getY() <= y + 10)
  }

  override def die() = {
    if (getAge() > maxLifeSpan) {
      super.die()
      WorldActor ! this
      exit()
    }
  }
}
