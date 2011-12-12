package edu.luc.edu.cs473.pp

object Configure {
  val WorldWidth: Int = 300			//world space width
  val WorldHeight: Int = 300		//world space height
  
  val ScopeRadius: Int = 10			//lynx hunt hare scope
  val HareRunStep: Int = 5			//every time hare step 
  val LynxRunStep: Int = 12			//every time lynx step
  
  val HareMaxAge: Int = 7			//max age of hare
  val HareBirthRate: Double = 0.06	//birth rate of hare
  var HareReproduce: Int = 4		//every time max reproduce how many bunnies
  val LynxMaxAge: Int = 15			//max age of lynx
  val LynxMaxEnergy: Int = 40		//max energy of lynx for initial lynx energy
  val EnergyGainPreHare: Int = 6	//energy for gain each hare
  val EnergyUseReproduce: Int = 15	//energy for reproduce kitten

  val InitialHares: Int = 60		//initial hares  Population
  val InitialLynx: Int = 8			//initial lynx Population
  
  val Runtime: Int = 100 			//simulation time
  val DisplayMessage: Boolean = false //only to display debug message
}