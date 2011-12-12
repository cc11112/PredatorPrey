package edu.luc.edu.cs473.pp

object WorldSpace {
  def getRandomX(): Int = (math.random * Configure.WorldWidth).toInt
  def getRandomY(): Int = (math.random * Configure.WorldHeight).toInt

  def getRandomX(x: Int, step: Int): Int = {
    val nextX: Int = x + direction() * (math.random * (step + 1)).toInt
    if (nextX < 0)
      Configure.WorldWidth + nextX
    else if (nextX > Configure.WorldWidth)
      nextX - Configure.WorldWidth
    else
      nextX
  }
   
  def getRandomY(y:Int, step: Int) : Int = {
    val nextY: Int = y + direction() * (math.random * (step + 1)).toInt
    if (nextY < 0)
      Configure.WorldHeight + nextY
    else if (nextY > Configure.WorldHeight)
      nextY - Configure.WorldHeight
    else
      nextY
  }
  
  /**
   * Select randomd direction
   */
  def direction(): Int = {
    if ((math.random * 100).toInt % 2 == 0)
      1
    else
      -1
  }
    
  
  /**
   * Check hare(x,y) is in lynx spot(xx,yy)
   */
  def isInScope( xx:Int, yy: Int, x: Int, y: Int) : Boolean   =
        (xx - Configure.ScopeRadius <= x && x <= xx + Configure.ScopeRadius
      && yy -Configure. ScopeRadius <= y && y <= yy + Configure.ScopeRadius)
}


