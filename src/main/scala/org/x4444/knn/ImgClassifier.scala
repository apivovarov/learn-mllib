package org.x4444.knn

import java.io.File
import javax.imageio.ImageIO

import scala.collection.mutable

/**
  * ImgClassifier classifies numeric images using k-Nearest Neighbors algo (Euclidian distance)
  */
object ImgClassifier {

  def getVector(f: String): Array[Int] = {
    val img = new File(f)
    ImageIO.read(img).getRGB(0, 0, 32, 32, null, 0, 32).map(x => if (x == -1) 0 else 1)
  }

  def getDistance(v1: Array[Int], v2: Array[Int]): Double = {
    val p = v1.zip(v2).map { case (a, b) => a - b }.map(x => x * x).sum
    math.sqrt(p)
  }

  def classify0(aArr: Array[Int], dataSet: List[(String, Array[Int])], k: Int): mutable.TreeSet[(Double, String)] = {
    val set = new mutable.TreeSet[(Double, String)]()
    dataSet.foreach { case (bLabel, bArr) =>
      val dist = getDistance(aArr, bArr)
      if (set.size < k) {
        set += ((dist, bLabel))
      } else {
        val max = set.last
        if (dist < max._1) {
          set -= max
          set += ((dist, bLabel))
        }
      }
    }
    set
  }
}
