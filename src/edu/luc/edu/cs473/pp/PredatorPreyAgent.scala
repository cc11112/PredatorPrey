package edu.luc.edu.cs473.pp

import scala.actors._

abstract class PredatorPreyAgent extends Actor{
	private var x: Int = -1
	private var y: Int = -1

	private var energy: Int = 0
	private var energyGain: Int = 0
	private var energyUse: Int = 0
	private var energyLimit: Int = 0
	
	private var age: Int = 0
	
	private var mateProbability: Double = 0
	
	private var sSpace: PredatorPreySpace = new PredatorPreySpace()
	def getX() :Int = x
    def getY(): Int = y
    def setXY(newX: Int, newY: Int) = {
        x = newX
        y = newY
    }
    
	def getEnergy(): Int = energy
	def setEnergy(energy:Int) = this.energy = energy
    def getEnergyGain() :Int = energyGain
    def setEnergyGain(energyGain: Int) = this.energyGain = energyGain;
    def getEnergyUse(): Int = energyUse
    def setEnergyUse(energyUse: Int) = this.energyUse = energyUse
    def getEnergyLimit(): Int = energyLimit
    def setEnergyLimit(energyLimit: Int) = this.energyLimit = energyLimit;
    
    def getMateProbability() : Double = mateProbability
    def setMateProbability(mateProbability: Double) = this.mateProbability = mateProbability
    
	def maxAge() : Int
	def die() ={
      if ( age > maxAge() ||  energy <= 0 )
    	  exit()
	  }
    
	def reproduce () = {}
	def run() = {}
}