package edu.luc.edu.cs473.pp

object WorldSpace {
  def getRandomX(): Int = (math.random * Configure.WorldWidth).toInt
  def getRandomY(): Int = (math.random * Configure.WorldHeight).toInt
}


