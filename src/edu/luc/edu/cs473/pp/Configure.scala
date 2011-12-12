package edu.luc.edu.cs473.pp

import java.awt.{Color}

object Configure {
  val WorldWidth: Int = 300			//world space width
  val WorldHeight: Int = 300		//world space height
  
  val HareColor: Color = Color.WHITE
  val LynxColor: Color = Color.RED
  val LynxCtrolColor: Color = Color.BLUE
  
  val HareSize: Int = 5
  val LynxSize: Int = 10
  
  val ScopeRadius: Int = 10			//lynx hunt hare scope
  val HareRunStep: Int = 25			//every time hare step 
  val LynxRunStep: Int = 35			//every time lynx step
  
  val HareMaxAge: Int = 7			//max age of hare
  val HareBirthRate: Double = 0.09	//birth rate of hare
  var HareReproduce: Int = 4		//every time max reproduce how many bunnies
  val LynxMaxAge: Int = 12			//max age of lynx
  val LynxMaxEnergy: Int = 40		//max energy of lynx for initial lynx energy
  val EnergyGainPreHare: Int = 6	//energy for gain each hare
  val EnergyUseReproduce: Int = 15	//energy for reproduce kitten

  val InitialHares: Int = 70		//initial hares  Population
  val InitialLynx: Int = 8			//initial lynx Population
  
  val Runtime: Int = 10 			//simulation time
  val DisplayMessage: Boolean = false //only to display debug message
}