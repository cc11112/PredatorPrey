package edu.luc.edu.cs473.pp

import swing._
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

case class DataPanel(circles: Seq[Circle], square: Seq[Square]) extends Panel {
  private var _circles = circles
  private var _squares = square

  def draw(h: Seq[Circle], l: Seq[Square]) = {
    _circles = h
    _squares = l
    repaint()
  }

  override def paintComponent(g: Graphics2D) {
    val color: Color = g.getColor()
    g.setColor(Color.GRAY)
    g.fillRect(0, 0, 300, 300)
    g.setColor(color)

    if (_circles != null) {
      for (h <- _circles)
        h.draw(g)
      g.setColor(color)
    }
    if (_squares != null) {
      for (l <- _squares)
        l.draw(g)
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


