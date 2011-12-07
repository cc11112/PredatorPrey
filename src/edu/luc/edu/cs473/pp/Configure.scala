package edu.luc.edu.cs473.pp

object Configure {
  val WorldWidth: Int = 300
  val WorldHeight: Int = 300
  val ScopeRadius: Int = 8
  
  val HareMaxAge: Int = 7
  val HareBirthRate: Double = 0.07
  var HareReproduce: Int = 4		//every time max reproduce how many bunnies
  val LynxMaxAge: Int = 15
  val LynxMaxEnergy: Int = 40
  val EnergyGainPreHare: Int = 6
  val EnergyUseReproduce: Int = 14

  val InitialHares: Int = 70
  val InitialLynx: Int = 5
  
  val Runtime: Int = 100 
  val DisplayMessage: Boolean = false
}