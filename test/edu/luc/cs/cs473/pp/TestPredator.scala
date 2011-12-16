package edu.luc.cs.cs473.pp

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.scalatest._

@RunWith(classOf[JUnitRunner])
class TestPredator extends FunSuite with BeforeAndAfterAll {

  override def beforeAll() {
    TestSuit.lock(true)
    WorldActor.reset()
  }

  override def afterAll() {
    TestSuit.unlock()
  }

  /**
   * Test Hare's age
   */
  def testActorAge(description: String, h: Hare, message: String, age: Int, dying: Boolean) = {
    test(description) {
      h ! message
      Thread.sleep(100)
      assert(h.getAge() === age)
      assert(h.getX() != -1)
      assert(h.getY() != -1)
      if (dying) {
        assert(h.getDying() === true)
      }
    }
  }

  /**
   * Test pot scope
   */
  def testScope(description: String, h: Hare, x: Int, y: Int, result: Boolean) = {
    test(description) {
      assert(h.isOnThisPot(x, y, Configure.HareScopeRadius) == result)
    }
  }

  val h1: Hare = new Hare(
    0,
    Configure.HareMaxAge,
    Configure.HareBirthRate,
    Configure.HareReproduce,
    -1,
    -1,
    Configure.HareRunStep)
  h1.start()

  /**
   * test age with alive message
   */
  for (i <- (1 to Configure.HareMaxAge + 1)) {
    testActorAge("testcase" + i, h1, "alive", i, i == Configure.HareMaxAge + 1);
  }

  val h2: Hare = new Hare(
    1,
    Configure.HareMaxAge,
    Configure.HareBirthRate,
    Configure.HareReproduce,
    0,
    0,
    Configure.HareRunStep)
  h2.start()

  /**
   * the other message can not update age
   */
  testActorAge("testAnyOtherMessage", h2, "any message", 1, false);

  /**
   * test die message
   */
  testActorAge("testdying", h2, "die", 1, true);

  val h3: Hare = new Hare(
    1,
    Configure.HareMaxAge,
    Configure.HareBirthRate,
    Configure.HareReproduce,
    100,
    100,
    Configure.HareRunStep)

  testScope("testIsInThisScope", h3, 100 + Configure.HareScopeRadius / 2, 100 - Configure.HareScopeRadius / 2, true)

  testScope("testIsOutThisScope", h3, 100 + Configure.HareScopeRadius * 2, 100 - Configure.HareScopeRadius * 2, false)
}
