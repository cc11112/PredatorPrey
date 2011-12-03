package edu.luc.edu.cs473.pp

import scala.actors._

abstract class PredatorPreyAgent(age: Int, maxAge: Int, startX: Int, startY: Int) extends Actor {
  private var x: Int = startX
  private var y: Int = startY

  private var currentAge: Int = age
  private var dying: Boolean = false

  def getX(): Int = x
  def getY(): Int = y
  def setXY(newX: Int, newY: Int) = {
    x = newX
    y = newY
  }

  def getAge() = currentAge
  def setAge() = { currentAge = currentAge + 1 }
  def setDying(): Unit = dying = true
  def getDying(): Boolean = dying

  def canReproduce(birthRate: Double): Boolean =
    math.random < birthRate && getAge() > 0

  def run() = {
    setXY(WorldSpace.getRandomX(), WorldSpace.getRandomY())
  }

  /**
   * quit this world
   */
  def quit() = {
    setDying()
    die()
    exit()
  }
  
  /**
   * Check is it time to die
   */
  def isDying(): Unit
  
  /**
   * die action
   */
  def die() = this ! None
}