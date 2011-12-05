package edu.luc.edu.cs473.pp

import scala.actors._

case class Lynx(
  age: Int,				//initial age
  maxLifeSpan: Int, 	//max-lynx-age
  energy: Int, 			//initialize energy  
  energyGain: Int,		//energy-per-hare-eaten
  energyUse: Int, 		//lynx-energy-to-reproduce
  startX: Int, startY: Int //initial lynx position
  )
  extends PredatorPreyAgent(age, maxLifeSpan, startX, startY) {

  private var currentEnergy: Int = energy

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
        case h: Hare => eatHares(h)
        case _ => exit()
      }
    }
  }

  /*
   * Consume energy
   */
  def consumeEnergy() = currentEnergy -= energyUse
  
  /*
   * Gain energy from eat hare
   */
  def addEnergy() = currentEnergy += energyGain

  /*
   * Try to catch hare
   */
  def tryToEat() = {
    WorldActor ! ("whereishare", this)
  }

  def eatHares(hare: Hare) = {
    addEnergy()
    hare ! None
  }
  
  /*
   * Try to Make Kittens
   */
  def tryToMakeKittens() = {
    if (canReproduce()) {
      //send world message to generate a new bunnies
      for (i <- (1 to reproduceNumber()))
        WorldActor !
          new Lynx(0, maxLifeSpan,
            currentEnergy / 2, // Kitten starts with 1/2 of the parents energy
            energyGain, energyUse,
            getX(), getY())
    }
  }

  /*
   * mate Probability
   */
  def reproduceNumber(): Int = (math.random * 5).toInt % 3

  /*
   * to check energy is meet the require condition
   */
  override def canReproduce(): Boolean = {
    (currentEnergy > energyUse) && (super.canReproduce())
  }

  override def die() = {
    if (getAge > maxLifeSpan || currentEnergy <= 0){
      WorldActor ! this
      super.die()
      exit()
    }
  }
}

