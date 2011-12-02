package edu.luc.edu.cs473.pp
import scala.actors._

class Predator(maxLifeSpan: Int) extends PredatorPreyAgent {
  override def maxAge(): Int = maxLifeSpan

  def act() {
    Actor.loop {
      react {
        case s: String => reply(s.length)
        case _ => exit()
      }
    }
  }
}