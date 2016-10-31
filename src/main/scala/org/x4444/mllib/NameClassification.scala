package org.x4444.mllib

import org.apache.spark.SparkContext
import org.apache.spark.mllib.classification.LogisticRegressionWithSGD
import org.apache.spark.mllib.linalg.{Vector, Vectors}
import org.apache.spark.mllib.regression.LabeledPoint
import org.x4444.core.Spark

/**
  * NameClassification
  */
object NameClassification {

  def main(args: Array[String]): Unit = {

//    val nameList = List(
//      getVector(true, true, false, false, 2),
//      getVector(true, false, false, false, 2),
//      getVector(false, true, false, false, 2),
//      getVector(true, true, false, false, 3),
//      getVector(true, true, false, false, 2),
//      getVector(true, true, true, false, 3),
//      getVector(true, true, false, false, 2),
//      getVector(true, true, false, false, 2),
//      getVector(true, true, false, true, 5),
//      getVector(true, true, false, false, 4)
//    )
//
//    val bizList = List(
//      getVector(false, false, false, false, 2),
//      getVector(false, false, false, true, 2),
//      getVector(false, false, false, false, 1),
//      getVector(false, false, false, false, 3),
//      getVector(false, false, false, true, 3),
//      getVector(false, false, false, true, 4),
//      getVector(false, false, false, true, 5),
//      getVector(false, false, false, false, 4),
//      getVector(false, false, false, false, 5),
//      getVector(true, false, false, false, 3),
//      getVector(false, true, false, true, 3),
//      getVector(true, false, false, true, 4),
//      getVector(false, false, false, true, 5),
//      getVector(false, false, false, false, 4),
//      getVector(false, true, false, false, 5)
//    )
//
//    val sc: SparkContext = Spark.getSc
//
//    val nameFeatures = sc.parallelize(nameList)
//    val bizFeatures = sc.parallelize(bizList)
//
//    val nameExamples = nameFeatures.map(features => LabeledPoint(1, features))
//    val bizExamples = bizFeatures.map(features => LabeledPoint(0, features))
//    val trainingData = nameExamples.union(bizExamples)
//    trainingData.cache()
//
//    val model = new LogisticRegressionWithSGD().run(trainingData)
//
//    val nameTest = model.predict(getVector(true, true, false, false, 2))
//    println(s"nameTest: $nameTest")
//
//    val bizTest = model.predict(getVector(false, false, false, false, 3))
//    println(s"bizTest: $bizTest")
  }

  def getVector(fn: Boolean, ln: Boolean, title: Boolean, bizType: Boolean, cnt: Int, fnPos: Int, lnPos: Int): Vector = {
    val arr = Array(
      if (fn) 1.0 else 0.0,
      if (ln) 1.0 else 0.0,
      if (title) 1.0 else 0.0,
      if (bizType) 1.0 else 0.0,
      if (cnt < 10) cnt / 10.0 else 1.0,
      posToDouble(fnPos),
      posToDouble(lnPos),
      posDist(fnPos, lnPos)
    )
    Vectors.dense(arr)
  }

  def getVector(s: String): Vector = {
    ???
  }

  def posToDouble(pos: Int): Double = {
    pos match {
      case 0 => 0.0
      case 1 => 0.1
      case 2 => 0.2
      case 3 => 0.5
      case 4 => 0.9
      case _ => 1.0
    }
  }

  def posDist(pos1: Int, pos2: Int): Double = {
    math.abs(pos1 - pos2) match {
      case 0 => 0
      case 1 => 0.1
      case 2 => 0.6
      case _ => 1.0
    }
  }

}
