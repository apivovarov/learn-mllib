package org.x4444

import java.io.File
import javax.imageio.ImageIO

/**
  * ImgReader
  */
object ImgReader {
  def main(args: Array[String]): Unit = {
    println(getVector("/tmp/one.bmp").toList)
  }

  def getVector(f: String): Array[Int] = {
    val img = new File(f)
    ImageIO.read(img).getRGB(0, 0, 32, 32, null, 0, 32).map(x => if (x == -1) 0 else 1)
  }
}
