package edu.luc.edu.cs473.pp

import scala.swing._
import event._

object WorldGUI extends SimpleSwingApplication {
  override def main(args: Array[String]) = Swing.onEDT { startup(args) }

  def top = new MainFrame {
    title = "Predator and Prey Agent simulation"
    val buttonExit = new Button {
      text = "Exit"
      action = Action("Exit") {
        System.exit(0)
      }
    }
    val buttonStart = new Button {
      text = "Start"
      action = Action("Start") {
        WorldActor.run(true)
      }
    }
    val buttonStop = new Button {
      text = "Stop"
      action = Action("Stop") {
        WorldActor.run(false)
      }
    }
    contents = new BoxPanel(Orientation.Vertical) {
      contents += buttonExit
      contents += buttonStart
      contents += buttonStop
      border = Swing.EmptyBorder(30, 30, 500, 500)
    }
  }
}