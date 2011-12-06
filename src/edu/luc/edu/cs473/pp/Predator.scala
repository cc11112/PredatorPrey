package edu.luc.edu.cs473.pp

import scala.actors._

case class Hare(
  age: Int, //initial age
  maxLifeSpan: Int, //max-hare-age 
  hareBirthRate: Double, //hare-birth-rate 
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

  /*
   * Try to make Bunnies
   */
  def tryToMakeBunnies() = {
    if (canReproduce(hareBirthRate)) {
      //send world message to generate a new bunnies
      for (i <- (1 to reproduceNumber()))
        WorldActor ! new Hare(0, maxLifeSpan, hareBirthRate, getX(), getY())
    }
  }

  /*
   * mate Probability 
   */
  def reproduceNumber(): Int = {
	 val n : Int = 1//(math.random * 5).toInt % 5 + 1
	 //println("reproduceNumber:" + n)
	 n
  }

  /*
   * Is this hare at this area
   */
  def isOnThisPot(x: Int, y: Int): Boolean = {
    //TODO:
    //search position
    (x - 10 <= getX() && getX() <= x + 10
      && y - 10 <= getY() && getY() <= y + 10)
  }

  override def die() = {
    if (getAge() >= maxLifeSpan) {
      WorldActor ! this
      super.die()
      exit()
    }
  }
}
