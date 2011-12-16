package edu.luc.cs.cs473.pp

import swing._
import event._
import java.awt.{ Color, Graphics, Cursor }
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

case class DataPanel(hares: Seq[Hare], lynx: Seq[Lynx]) extends BoxPanel(Orientation.Vertical){
  private var _hares = hares
  private var _lynx = lynx
  private var interActiveLynx: Option[Lynx] = None

  cursor = new Cursor(Cursor.CROSSHAIR_CURSOR)
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
    if (interActiveLynx.isDefined) {
      interActiveLynx.get.setContorlled(false)
      interActiveLynx = None
    }
    cursor = new Cursor(Cursor.CROSSHAIR_CURSOR)
  }

  def searchLynx(x: Int, y: Int): Option[Lynx] = {
    cursor = new Cursor(Cursor.HAND_CURSOR)
    var lynx: Option[Lynx] = None
    if (_lynx != null) {
      lynx = _lynx.find(e => e.isOnThisPot(x, y, Configure.LynxScopeRadius))
    }
    if (lynx.isDefined) {
      lynx.get.setContorlled(true)
      cursor = new Cursor(Cursor.MOVE_CURSOR)
    }
    lynx
  }

  def draw(h: Seq[Hare], l: Seq[Lynx]) = {
    _hares = h
    _lynx = l
    repaint()
  }

  def drawLyx(l: Lynx, g: Graphics2D) = {
    val c: Color = if (l.getContorlled()) Configure.LynxCtrolColor else Configure.LynxColor
    new Square(l.getX(), l.getY(), Configure.LynxSize, Configure.LynxSize, c).draw(g)
  }

  override def paintComponent(g: Graphics2D) {
    super.paintComponent(g)
    //g.clearRect(0, 0, Configure.WorldWidth, Configure.WorldHeight + 30)

    val color: Color = g.getColor()
    g.setColor(Color.GRAY)
    g.fillRect(0, 0, Configure.WorldWidth, Configure.WorldHeight)
    g.setColor(color)

    if (_hares != null) {
      for (h <- _hares)
        if (!h.getDying())
        	new Circle(h.getX(), h.getY(), Configure.HareSize, Configure.HareColor).draw(g)
      g.setColor(color)        	
      g.drawString("Hares Population: " + _hares.count(e => !e.getDying()).toString(), 10, size.height - 60)
    }
    if (_lynx != null) {
      for (l <- _lynx)
        if (!l.getDying())
        	drawLyx(l, g)
      g.setColor(color)        	
      g.drawString("Lynx Population: " + _lynx.count(e => !e.getDying()).toString(), 10, size.height - 45)
    }

    //hint
    g.drawString("Press left mouse button to catch one of lynx.", 10, size.height - 30)
    if (interActiveLynx != None && interActiveLynx.isDefined)
      g.drawString("Move mouse to move lynx.", 10, size.height - 15)
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


