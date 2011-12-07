package edu.luc.edu.cs473.pp

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite

@RunWith(classOf[JUnitRunner])
class TestWorldActor extends FunSuite {

  /**
   * Test world actor
   */
  def testHares(description: String, h: Hare, result: Int) = {
    test(description) {
      WorldActor ! h
      Thread.sleep(100)
      assert(WorldActor.getHaresCount() === result)
    }
  }

  def testLynx(description: String, l: Lynx, result: Int) = {
    test(description) {
      WorldActor ! l
      Thread.sleep(100)
      assert(WorldActor.getLynxCount() === result)
    }
  }

  WorldActor.start()
  
  testHares("test new born bunny:",
    new Hare(0, Configure.HareMaxAge, Configure.HareBirthRate, Configure.HareReproduce, 0, 0),
    1)

  testLynx("test new born kitten:",
    new Lynx(0, Configure.LynxMaxAge, Configure.EnergyUseReproduce, Configure.EnergyGainPreHare, Configure.EnergyUseReproduce, 0, 0),
    1)

}
 