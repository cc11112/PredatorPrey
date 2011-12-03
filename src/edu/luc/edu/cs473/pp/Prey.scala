package edu.luc.edu.cs473.pp

import scala.actors._

case class Lynx(
  age: Int, //initial age
  maxLifeSpan: Int, //max-lynx-age
  energy: Int, //initialize energy  
  energyGain: Int, //energy-per-hare-eaten
  energyUse: Int, //lynx-energy-to-reproduce
  startX: Int, startY: Int //initial lynx position
  )
  extends PredatorPreyAgent(age, maxLifeSpan, startX, startY) {

  private var currentEnergy: Int = energy

  def act() {
    Actor.loop {
      react {
        case "alive" => {
          run()
          consumeEnergy() 	//"set-energy"
          isDying()
          tryToEat()		//search hare
          tryToMakeKitten()
          setAge()
          isDying()
        }
        case "die" => quit()
        case h: Hare => eatHaresIfExists(h)
        case _ => exit()
      }
    }
  }

  /**
   * Consume energy
   */
  def consumeEnergy() = currentEnergy -= 1

  /**
   * Gain energy from eat hare
   */
  def addEnergy() = currentEnergy += energyGain

  /**
   * Try to catch hare
   */
  def tryToEat() = {
    WorldActor ! ("whereishare", this)
  }

  def eatHaresIfExists(hare: Hare) = {
    if (!getDying()) {
    	hare ! "die"            //kill this hare
    	addEnergy()
    }
  }

  /**
   * Try to Make Kittens
   */
  def tryToMakeKitten() = {
    
    if (canReproduce()) {
      //send world message to generate a new Kittens
      WorldActor !
        new Lynx(0, maxLifeSpan,
          currentEnergy / 2 + 1, // Kitten starts with 1/2 of the parents energy
          energyGain, energyUse,
          getX(), getY())

      //reduce current energy
      currentEnergy /= 2
    }
  }
  
  /**
   * to check energy is meet the require condition
   */
  def canReproduce(): Boolean = 
    (currentEnergy > energyUse) && getAge() > 0 
  
  override def die() = WorldActor ! this
    
  override def isDying() = {
    //println("Age: " +getAge() + " Energy: " + currentEnergy)
    if (getAge() > maxLifeSpan || currentEnergy < 0) {
    	quit()
    }
  }
}

