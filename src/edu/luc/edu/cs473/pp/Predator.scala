package edu.luc.edu.cs473.pp

import scala.actors._

case class Hare(
  age: Int, //initial age
  maxLifeSpan: Int, //max-hare-age 
  hareBirthRate: Int, //hare-birth-rate 
  startX: Int, startY: Int //initial lynx position
  )
  extends PredatorPreyAgent(age, maxLifeSpan, startX, startY) {

  def act() {
    Actor.loop {
      react {
        case "run" => run()
        case "alive" => {
          tryToMakeBunnies()
          setAge()
        }
        case "die" => die()
        case _ => exit()
      }
    }
  }

  /*
   * Try to make Bunnies
   */
  def tryToMakeBunnies() = {
    if (canReproduce()) {
      //send world message to generate a new bunnies
      for (i <- (1 to reproduceNumber()))
        WorldActor ! new Hare(0, maxLifeSpan, hareBirthRate, getX(), getY())
    }
  }

  /*
   * mate Probability and birth rate
   */
  def reproduceNumber(): Int = (math.random * 100).toInt % hareBirthRate + 1

  /*
   * Is this hare at this area
   */
  def isOnThisPot(x: Int, y: Int): Boolean = {
    //TODO:
    //search position
    (x - 5 <= getX() && getX() <= x + 5
      && y - 5 <= getY() && getY() <= y + 5)
  }

  override def die() = {
    if (getAge > maxLifeSpan) {
      WorldActor ! this
      super.die()
      exit()
    }
  }
}
