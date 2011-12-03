package edu.luc.edu.cs473.pp

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite

@RunWith(classOf[JUnitRunner])
class TestPredator extends FunSuite {

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

  val h1: Hare = new Hare(
    0,
    Configure.HareMaxAge,
    Configure.HareBirthRate,
    Configure.HareReproduce,
    -1,
    -1)
  h1.start()

  // 
  for (i <- (1 to Configure.HareMaxAge + 1)) {
    testActorAge("testcase" + i, h1, "alive", i, i == Configure.HareMaxAge + 1);
  }
  
  
  val h2: Hare = new Hare(
    1,
    Configure.HareMaxAge,
    Configure.HareBirthRate,
    Configure.HareReproduce,
    0,
    0)
  h2.start()

  testActorAge("testdying", h2, "die", 1, true);
}
