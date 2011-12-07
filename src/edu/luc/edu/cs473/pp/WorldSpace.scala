package edu.luc.edu.cs473.pp

object WorldSpace {
  def getRandomX(): Int = (math.random * Configure.WorldWidth).toInt
  def getRandomY(): Int = (math.random * Configure.WorldHeight).toInt
  
  /**
   * Check hare(x,y) is in lynx spot(xx,yy)
   */
  def isInScope( xx:Int, yy: Int, x: Int, y: Int) : Boolean   =
        (xx - Configure.ScopeRadius <= x && x <= xx + Configure.ScopeRadius
      && yy -Configure. ScopeRadius <= y && y <= yy + Configure.ScopeRadius)
}


