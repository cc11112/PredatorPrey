package edu.luc.cs.cs473.pp

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.scalatest._


@RunWith(classOf[JUnitRunner])
class TestWorldActor extends FunSuite with BeforeAndAfterAll {

  override def beforeAll() {
    TestSuit.lock(true)
    WorldActor.reset()
  }

  override def afterAll() {
    TestSuit.unlock()
  }

  /**
   * Test world actor to handle hares
   */
  def testHares(description: String, h: Hare, result: Int) = {
    test(description) {
      if (h != null) {
        WorldActor ! h
        Thread.sleep(200)
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

      Thread.sleep(200)

      assert(WorldActor.getLynxCount() === lynx)
      assert(WorldActor.getHaresCount() === hare)
    }
  }

  def testPositionofHare(description: String, hare: Hare, lynx: Lynx, result: Int) = {
    test(description) {

      val e = lynx.getEnergy()
      lynx ! (hare.getX() + Configure.HareScopeRadius / 2, hare.getY() - Configure.HareScopeRadius / 2)
      WorldActor ! ("whereishare", lynx)
      Thread.sleep(100)
      //lynx should got the hare
      assert(lynx.getEnergy() === e + Configure.EnergyGainPreHare)
      assert(WorldActor.getHaresCount() === result)
    }
  }

  WorldActor.reset()
  WorldActor.start()

  val h = new Hare(0, Configure.HareMaxAge, Configure.HareBirthRate, Configure.HareReproduce, 0, 0, Configure.HareRunStep)

  testHares("test new born bunny:", h, 1)

  val l = new Lynx(0, Configure.LynxMaxAge, Configure.EnergyUseReproduce, Configure.EnergyGainPreHare, Configure.EnergyUseReproduce, 0, 0, Configure.LynxRunStep)

  testLynx("test new born kitten:", l, 1)

  testHares("test another new born bunny:",
    new Hare(Configure.HareMaxAge - 1, Configure.HareMaxAge, Configure.HareBirthRate, Configure.HareReproduce, 0, 0, Configure.HareRunStep),
    2)

  testLynx("test another new born kitten:",
    new Lynx(Configure.LynxMaxAge, Configure.LynxMaxAge, Configure.EnergyUseReproduce, Configure.EnergyGainPreHare, Configure.EnergyUseReproduce, 0, 0, Configure.LynxRunStep),
    2)

  //test position has hare
  //one of hares will be killed, so, only one is left
  testPositionofHare("test position of hare", h, l, 1)

}

