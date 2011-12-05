package edu.luc.edu.cs473.pp

import scala.actors._

abstract class PredatorPreyAgent(age:Int, maxAge: Int, startX: Int, startY: Int) extends Actor {
  private var x: Int = startX
  private var y: Int = startY
  
  private var currentAge: Int = age
  private var dying: Boolean = false
  
  private var sSpace: PredatorPreySpace = new PredatorPreySpace()
  def getX(): Int = x
  def getY(): Int = y
  def setXY(newX: Int, newY: Int) = {
    x = newX
    y = newY
  }
  
  def getAge() = currentAge
  def setAge() = { currentAge = currentAge + 1 }
  def die(): Unit = dying = true
  def getDying(): Boolean = dying
  
  //TODO:
  def canReproduce(): Boolean =  math.random * 800 < 200

  def run() = {
    //TODO: Move randomly in the world.
  }
}