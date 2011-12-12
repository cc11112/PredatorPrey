package edu.luc.edu.cs473.pp

import swing._
import event._
import java.awt.{ Color, Graphics }
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

abstract class Shape() {
  def draw(g: Graphics): Unit
  def area: Double
}

class Circle(var x: Int, var y: Int, radius: Int, fillColor: Color) extends Shape() {
  def draw(g: Graphics): Unit = {
    if (x + radius > Configure.WorldWidth) {
      x = Configure.WorldWidth - radius
    }
    if (y + radius > Configure.WorldHeight) {
      y = Configure.WorldHeight - radius
    }

    if (fillColor == null) {
      g.drawOval(x, y, radius, radius)
    } else {
      g.setColor(fillColor);
      g.fillOval(x, y, radius, radius)
    }
  }

  def area: Double = {
    var back = math.Pi * radius
    back * radius
  }
}

class Square(var x: Int, var y: Int, width: Int, height: Int, fillColor: Color) extends Shape {
  def draw(g: Graphics): Unit = {
    if (x + width > Configure.WorldWidth) {
      x = Configure.WorldWidth - width
    }
    if (y + height > Configure.WorldHeight) {
      y = Configure.WorldHeight - height
    }
    if (fillColor == null) {
      g.drawRect(x, y, width, height)
    } else {
      g.setColor(fillColor)
      g.fillRect(x, y, width, height)
    }
  }

  def area: Double = width * height
}

case class DataPanel(hares: Seq[Hare], lynx: Seq[Lynx]) extends Panel {
  private var _hares = hares
  private var _lynx = lynx
  private var interActiveLynx: Option[Lynx] = None

  focusable = true
  listenTo(mouse.clicks, mouse.moves, keys)

  reactions += {
    case e: MousePressed => interActiveLynx = searchLynx(e.point.getX().toInt, e.point.getY().toInt)
    case e: MouseDragged => if (interActiveLynx.isDefined) {
      interActiveLynx.get.setXY(e.point.getX().toInt, e.point.getY().toInt)
    }
    case e: MouseReleased => releaseLynx() 
    case _: FocusLost => repaint()
  }

  def releaseLynx() = {
    if (interActiveLynx.isDefined){
    	interActiveLynx.get.setContorlled(false)
    	interActiveLynx = None
    }
  }
  
  def searchLynx(x: Int, y: Int): Option[Lynx] = {
    val lynx:Option[Lynx] = _lynx.find(e => e.isOnThisPot(e.getX(), e.getY()))
    if (lynx.isDefined){
      lynx.get.setContorlled(true)
    }
    lynx
  }
  
  def draw(h: Seq[Hare], l: Seq[Lynx]) = {
    _hares = h
    _lynx = l
    repaint()
  }

  override def paintComponent(g: Graphics2D) {
    val color: Color = g.getColor()
    g.setColor(Color.GRAY)
    g.fillRect(0, 0, 300, 300)
    g.setColor(color)

    if (_hares != null) {
      for (h <- _hares)
        new Circle(h.getX(), h.getY(), Configure.HareSize, Configure.HareColor).draw(g)
      g.setColor(color)
    }
    if (_lynx != null) {
      for (l <- _lynx){
        val c:Color = if (l.getContorlled()) Configure.LynxCtrolColor  else Configure.LynxColor 
        new Square(l.getX(), l.getY(),
            Configure.LynxSize, 
            Configure.LynxSize, 
            c
            ).draw(g)
      }
      g.setColor(color)
    }
  }
}

case class ImagePanel(rows0: Int, cols0: Int) extends GridPanel(rows0, cols0) {
  private var _imagePath = ""
  private var bufferedImage: BufferedImage = null

  def imagePath = _imagePath

  def imagePath_=(value: String) {
    _imagePath = value
    bufferedImage = ImageIO.read(new File(_imagePath))
  }

  override def paintComponent(g: Graphics2D) = {
    if (null != bufferedImage) g.drawImage(bufferedImage, 0, 0, null)
  }
}                                                                             


