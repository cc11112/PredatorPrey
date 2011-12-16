package edu.luc.cs.cs473.pp

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
          case (hares: Seq[Hare], lynx: Seq[Lynx]) => dataPanel.draw(hares, lynx)
          case "exit" => println("exiting..."); exit
          case x: Any => println("Warning: Unknown message! " + x)
        }
      }
    }
  }

  val dataPanel = new DataPanel(null, null) {
    border = Swing.EmptyBorder(30, 30, Configure.WorldHeight + 60, Configure.WorldWidth + 60)
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
        WorldActor.run(true)
      }
    }

    val buttonStop = new Button {
      text = "Stop"
      action = Action("Stop") {
        WorldActor.run(false)
      }
    }

    val imagePanel = new ImagePanel(0, 1) {
      contents += buttonStart
      contents += buttonStop
      contents += buttonExit
    }

    contents = new BoxPanel(Orientation.Vertical) {

      contents += imagePanel
      contents += dataPanel

      val s = new Dimension(Configure.WorldWidth + 100, Configure.WorldHeight + 150)

      minimumSize = s
      maximumSize = s
      preferredSize = s
    }
    
  //  preferredSize = new Dimension(Configure.WorldWidth + 300, Configure.WorldHeight + 350)
    
    ShapeDrawingActor.start()
    centerOnScreen
    listenTo(this)
  }
}