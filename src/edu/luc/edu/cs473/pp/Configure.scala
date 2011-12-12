package edu.luc.edu.cs473.pp

object Configure {
  val WorldWidth: Int = 300
  val WorldHeight: Int = 300
  val ScopeRadius: Int = 10
  val HareRunStep: Int = 5
  val LynxRunStep: Int = 15
  
  val HareMaxAge: Int = 7
  val HareBirthRate: Double = 0.06
  var HareReproduce: Int = 4		//every time max reproduce how many bunnies
  val LynxMaxAge: Int = 15
  val LynxMaxEnergy: Int = 40
  val EnergyGainPreHare: Int = 6
  val EnergyUseReproduce: Int = 15

  val InitialHares: Int = 60
  val InitialLynx: Int = 8
  
  val Runtime: Int = 100 
  val DisplayMessage: Boolean = false //only to display debug message
}