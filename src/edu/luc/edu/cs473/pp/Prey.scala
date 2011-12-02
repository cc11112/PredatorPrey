package edu.luc.edu.cs473.pp

import scala.actors._

class Prey(minLifeSpan: Int,
  maxLifeSpan: Int,
  maximumEnergy: Int,
  energyGain: Int,
  energyUse: Int,
  mateProbability: Double,
  open: Int) extends PredatorPreyAgent {

  override def maxAge(): Int = maxLifeSpan

  def act() {
    Actor.loop {
      react {
        case "die" => die()
      }
    }
  }

}