package org.x4444.knn

import org.scalatest.FunSuite
import org.x4444.knn.ImgClassifier._

/**
  * ImgClassifierSuite
  */
class ImgClassifierSuite extends FunSuite {
  test("classify0") {
    val dir = "files/img-class"
    val dataSet = Map(
      "1" -> "one.bmp",
      "2" -> "two.bmp",
      "3" -> "three.bmp",
      "7" -> "seven.bmp",
      "8" -> "eight.bmp"
    ).mapValues(file => getVector(s"$dir/$file")).toList

    val in = getVector(s"$dir/test-seven.bmp")
    val res = classify0(in, dataSet, 3)
    res.foreach { case (dist, label) => println(s"label: $label, dist: $dist") }

    List(
      ("test-one.bmp", "1"),
      ("test-two.bmp", "2"),
      ("test-three.bmp", "3"),
      ("test-seven.bmp", "7"),
      ("test-eight.bmp", "8")
    ).foreach { case (fileName, expLabel) =>
      val res = classify0(getVector(s"$dir/$fileName"), dataSet, 1).head
      assert(res._2 == expLabel)
    }
  }
}
