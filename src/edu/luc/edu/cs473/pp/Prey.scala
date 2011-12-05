package edu.luc.edu.cs473.pp

import scala.actors._

case class Lynx(minLifeSpan: Int,
  maxLifeSpan: Int,
  maximumEnergy: Int,
  energyGain: Int,
  energyUse: Int,
  mateProbability: Double,
  open: Int) extends PredatorPreyAgent(maxLifeSpan) {

  def act() {
    Actor.loop {
      react {
        case "alive" => {
          consumeEnergy() //"set-energy"
          tryToEat()
          tryToMakeKittens()
          setAge()
        }
        case "die" => die()
        case _ => exit()
      }
    }
  }

  def consumeEnergy() = setEnergy(getEnergy() - energyUse)

  def tryToEat() = {
    //TODO:
    //
  }

  def tryToMakeKittens() = {
    //TODO:
  }

  override def die() = {
    if (getAge > maxLifeSpan || getEnergy() <= 0)
      exit()
  }
}

