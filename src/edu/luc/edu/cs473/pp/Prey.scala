package edu.luc.edu.cs473.pp

import scala.actors._

case class Lynx(maxLifeSpan: Int,
  maximumEnergy: Int, energyGain: Int, energyUse: Int,
  startX: Int, startY: Int)
  extends PredatorPreyAgent(maxLifeSpan, startX, startY) {

  def act() {
    Actor.loop {
      react {
        case "run" => run()
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

  override def canReproduce(): Boolean = getEnergy() > getEnergyUse()

  override def die() = {
    if (getAge > maxLifeSpan || getEnergy() <= 0)
      exit()
  }
}

