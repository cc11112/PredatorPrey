package edu.luc.edu.cs473.pp

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite


@RunWith(classOf[JUnitRunner])
class TestPrey extends FunSuite {
  
  def testActor(description: String, message: String) = {
    test(description) {
      assert(message === "ok")
    }
  }
  
  testActor("testcase0", "ok");
}
