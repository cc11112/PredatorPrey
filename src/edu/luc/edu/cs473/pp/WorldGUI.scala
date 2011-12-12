package edu.luc.edu.cs473.pp

import scala.swing._
import event._
import scala.actors._
import scala.actors.Actor._

object WorldGUI extends SimpleSwingApplication {
  override def main(args: Array[String]) = Swing.onEDT { startup(args) }

  object ShapeDrawingActor extends Actor {
    def act() {
      loop {
        receive {
          case (hares: Seq[Circle], lynx: Seq[Square]) => dataPanel.draw(hares, lynx)
          case "exit" => println("exiting..."); exit
          case x: Any => println("Error: Unknown message! " + x)
        }
      }
    }
  }

  val dataPanel = new DataPanel(null, null) {
    border = Swing.EmptyBorder(30, 100, Configure.WorldWidth, Configure.WorldHeight)
  }

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
        ShapeDrawingActor.start()
        WorldActor.run(true)
      }
    }
    val buttonStop = new Button {
      text = "Stop"
      action = Action("Stop") {
        WorldActor.run(false)
        Thread.sleep(1000)
      }
    }

//    val ImagePanel = new ImagePanel(10, 1) {
//      contents += buttonStart
//      contents += buttonStop
//      contents += buttonExit
//    }
//
    contents = new BoxPanel(Orientation.Vertical) {

      contents += buttonStart
      contents += buttonStop
      contents += buttonExit
      contents += dataPanel

      // border = Swing.EmptyBorder(30, 30, 500, 300)

      val s = new Dimension(500, 700)

      minimumSize = s
      maximumSize = s
      preferredSize = s
    }
    
     centerOnScreen
     listenTo(this)
  }
}