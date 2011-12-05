package edu.luc.edu.cs473.pp

import scala.actors._

class Turtle(startX: Int, startY: Int, maxX: Int, maxY: Int) extends Actor {
  private var x: Int = startX
  private var y: Int = startY

  def act() {
    Actor.loop {
      react {
        case "take-a-step" => takeAStep()
        case _ => exit()
      }
    }
  }

  /*
   *  rotate anywhere between 50 degree CCW to degree 50 degree CW
   */
  def takeAStep() = {

    //TODO:
    //Turtle rotates between right(CW) a random number of degree ( 0 ~ 50)
    rightRandom(50)
    //
    leftRandom(50)
    forward(1)

    if (outOfScope())
      exit()
  }

  def outOfScope(): Boolean = x > maxX || x < 0 || y > maxY || y < 0

  def rightRandom(r: Int) = {
    //TODO:
    //Calucate rotate right direction
  }

  def leftRandom(r: Int) = {
    //TODO:
    //Calucate rotate left direction
    //Math.random().
  }

  def forward(stet: Int) = {
    //TODO:
    //calculate x, y on this direction
  }
}