package edu.luc.edu.cs473.pp
import scala.actors._

case class Hare(age: Int, maxLifeSpan: Int, hareBirthRate: Double, startX: Int, startY: Int)
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

  def tryToMakeBunnies() = {
    //TODO:
    if (canReproduce()) {
      //send world message to generate a new bunnies
      for (i <- (1 to reproduceNumber()))
        WorldActor ! new Hare(0, maxLifeSpan, hareBirthRate, getX(), getY())
    }
  }

  override def canReproduce(): Boolean =
    math.random * 800 < 200

  def reproduceNumber(): Int = (math.random * 10).toInt % 8 + 1

  override def die() = {
    if (getAge > maxLifeSpan){
      WorldActor ! this
      super.die()
      exit()
    }
  }
}
