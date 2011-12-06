package edu.luc.edu.cs473.pp

object Configure {
  val WorldWidth: Int = 300
  val WorldHeight: Int = 300
  val HareMaxAge: Int = 6
  val HareBirthRate: Double = 0.07
  var HareReproduce: Int = 5		//every time max reproduce how many bunnies
  val LynxMaxAge: Int = 10
  val LynxMaxEnergy: Int = 30
  val lynxMaxReproduce: Int = 3		//every time max reproduce how many kittens
  val EnergyGainPreHare: Int = 5
  val EnergyUseReproduce: Int = 10

  val InitialHares: Int = 60
  val InitialLynx: Int = 5
  
  val Runtime: Int = 100 
  val DisplayMessage: Boolean = false
}