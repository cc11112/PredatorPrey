package edu.luc.edu.cs473.pp

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite

@RunWith(classOf[JUnitRunner])
class TestPrey extends FunSuite {

  /**
   * Test Lynx age
   */
  def testActor(description: String, l: Lynx, message: String, age: Int, energy: Int, dying: Boolean) = {
    test(description) {
      
      l ! message
      Thread.sleep(100)
      
      assert(l.getAge() === age)
      assert(l.getEnergy() === energy)
      assert(l.getX() != -1)
      assert(l.getY() != -1)
      
      if (dying) {
        assert(l.getDying() === true)
      }
    }
  }

  val l1: Lynx = new Lynx(
    0,
    Configure.LynxMaxAge,
    Configure.EnergyUseReproduce,
    Configure.EnergyGainPreHare,
    Configure.EnergyUseReproduce,
    -1,
    -1)
  l1.start()

  /**
   * test age with alive message
   */
  for (i <- (1 to Configure.LynxMaxAge -1 )) {
    testActor("testcase" + i, l1, "alive", i, Configure.EnergyUseReproduce - i,
      (Configure.EnergyUseReproduce - i < 0 || i > Configure.LynxMaxAge))
  }
}
