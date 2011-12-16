package edu.luc.cs.cs473.pp

import java.awt.{Color}

object Configure {
  val WorldWidth: Int = 400			//world space width
  val WorldHeight: Int = 400		//world space height
  
  val HareColor: Color = Color.WHITE	//color of hare
  val LynxColor: Color = Color.RED		//color of lynx
  val LynxCtrolColor: Color = Color.BLUE //color of lynx is manulled by person
  
  val HareSize: Int = 6				//size of hare on UI
  val LynxSize: Int = 12			//size of lynx on UI
  
  val HareScopeRadius: Int = 10		//lynx hunt hare scope
  val LynxScopeRadius: Int = 25		//lynx hunt hare scope
  val HareRunStep: Int = 15			//every time hare step 
  val LynxRunStep: Int = 26			//every time lynx step
  
  val HareMaxAge: Int = 7			//max age of hare
  val HareBirthRate: Double = 0.08	//birth rate of hare
  var HareReproduce: Int = 5		//every time max reproduce how many bunnies
  val LynxMaxAge: Int = 11			//max age of lynx
  val LynxMaxEnergy: Int = 40		//max energy of lynx for initial lynx energy
  val EnergyGainPreHare: Int = 6	//energy for gain each hare
  val EnergyUseReproduce: Int = 15	//energy for reproduce kitten

  val InitialHares: Int = 70		//initial hares  Population
  val InitialLynx: Int = 8			//initial lynx Population
  
  val Ticker: Int = 1000			//clock ticker interval
  val Runtime: Int = 10 			//simulation time
  val DisplayMessage: Boolean = false //only to display debug message
}