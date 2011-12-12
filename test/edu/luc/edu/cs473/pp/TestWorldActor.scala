package edu.luc.edu.cs473.pp

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite

@RunWith(classOf[JUnitRunner])
class TestWorldActor extends FunSuite {

  /**
   * Test world actor to handle hares
   */
  def testHares(description: String, h: Hare, result: Int) = {
    test(description) {
      if (h != null) {
        WorldActor ! h
        Thread.sleep(100)
      }
      assert(WorldActor.getHaresCount() === result)
    }
  }

  /**
   * test world actor to handle lynx
   */
  def testLynx(description: String, l: Lynx, result: Int) = {
    test(description) {
      if (l != null) {
        WorldActor ! l
        Thread.sleep(100)
      }
      assert(WorldActor.getLynxCount() === result)
    }
  }

  /**
   * test simulate of world actor
   */
  def testSimluate(description: String, message: String, lynx: Int, hare: Int) = {
    test(description) {

      WorldActor ! message
      Thread.sleep(100)

      assert(WorldActor.getLynxCount() === lynx)
      assert(WorldActor.getHaresCount() === hare)
    }
  }

  def testPositionofHare(description: String, hare: Hare, lynx: Lynx, result: Int) = {
    test(description) {

      val e = lynx.getEnergy()
      lynx ! (hare.getX() + Configure.ScopeRadius / 2, hare.getY() - Configure.ScopeRadius / 2 )
      WorldActor ! ("whereishare", lynx)
      Thread.sleep(100)
      //lynx should got the hare
      assert(lynx.getEnergy() === e + Configure.EnergyGainPreHare)
      assert(WorldActor.getHaresCount() === result)
    }
  }

  WorldActor.start()

  val h = new Hare(0, Configure.HareMaxAge, Configure.HareBirthRate, Configure.HareReproduce, 0, 0)

  testHares("test new born bunny:", h, 1)

  val l = new Lynx(0, Configure.LynxMaxAge, Configure.EnergyUseReproduce, Configure.EnergyGainPreHare, Configure.EnergyUseReproduce, 0, 0)

  testLynx("test new born kitten:", l, 1)

  testHares("test another new born bunny:",
    new Hare(Configure.HareMaxAge - 1, Configure.HareMaxAge, Configure.HareBirthRate, Configure.HareReproduce, 0, 0),
    2)

  testLynx("test another new born kitten:",
    new Lynx(Configure.LynxMaxAge, Configure.LynxMaxAge, Configure.EnergyUseReproduce, Configure.EnergyGainPreHare, Configure.EnergyUseReproduce, 0, 0),
    2)

  //one of lynx should be die
  testSimluate("test simulate", "ticker", 1, 2)

  //one of hare should be die
  testSimluate("test simulate ticker 2", "ticker", 1, 1)

  //test position has hare
  //hare is killed
  testPositionofHare("test position of hare", h, l, 0)

}

