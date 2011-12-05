package edu.luc.edu.cs473.pp
import scala.actors._

case class Hare(maxLifeSpan: Int) extends PredatorPreyAgent(maxLifeSpan) {

  def act() {
    Actor.loop {
      react {
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
      new Hare(maxLifeSpan)
    }
  }

  override def die() = {
    if (getAge > maxLifeSpan)
      exit()
  }
}
