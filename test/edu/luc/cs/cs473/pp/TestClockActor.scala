package edu.luc.cs.cs473.pp

import scala.actors.Actor
import scala.actors.Actor._
import org.specs._

/**
 * Try use spec framework to clock actor
 * 
 */
object TestClockActorSpec extends Specification {
  "ClockActorController" should{
    "send a ticker message" in{
        var res = false
        val a = actor{}
        val mos = {ClockActor !? "ticker"}
        mos match{
             case "ticker" => {
               res = true
               ClockActor ! "stop"		   
             }
             case _ => res = false
        }
        res must beTrue
    }
    
    "send a stop message" in{
        var res = false
        val a = actor{}
        val mos = {ClockActor !? "stop"}
        mos match{
             case "stop" => res = true
             case _ => res = false
        }
        res must beTrue
    }
  }
  
  ClockActor.startClock()
}
