package edu.luc.cs.cs473.pp

object TestSuit {
  private var StartWorldActor: Boolean = false
  def lock(v: Boolean) = {
    synchronized {
      while (StartWorldActor) {
        Thread.sleep(100)
      }
      StartWorldActor = v
    }
  }
  def unlock() = {
    StartWorldActor = false
  }
}