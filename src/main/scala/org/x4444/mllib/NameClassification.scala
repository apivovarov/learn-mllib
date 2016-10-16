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

    val nameList = List(
      getVector(true, true, false, false, 2),
      getVector(true, false, false, false, 2),
      getVector(false, true, false, false, 2),
      getVector(true, true, false, false, 3),
      getVector(true, true, false, false, 2),
      getVector(true, true, true, false, 3),
      getVector(true, true, false, false, 2),
      getVector(true, true, false, false, 2),
      getVector(true, true, false, true, 5),
      getVector(true, true, false, false, 4)
    )

    val bizList = List(
      getVector(false, false, false, false, 2),
      getVector(false, false, false, true, 2),
      getVector(false, false, false, false, 1),
      getVector(false, false, false, false, 3),
      getVector(false, false, false, true, 3),
      getVector(false, false, false, true, 4),
      getVector(false, false, false, true, 5),
      getVector(false, false, false, false, 4),
      getVector(false, false, false, false, 5),
      getVector(true, false, false, false, 3),
      getVector(false, true, false, true, 3),
      getVector(true, false, false, true, 4),
      getVector(false, false, false, true, 5),
      getVector(false, false, false, false, 4),
      getVector(false, true, false, false, 5)
    )

    val sc: SparkContext = Spark.getSc

    val nameFeatures = sc.parallelize(nameList)
    val bizFeatures = sc.parallelize(bizList)

    val nameExamples = nameFeatures.map(features => LabeledPoint(1, features))
    val bizExamples = bizFeatures.map(features => LabeledPoint(0, features))
    val trainingData = nameExamples.union(bizExamples)
    trainingData.cache()

    val model = new LogisticRegressionWithSGD().run(trainingData)

    val nameTest = model.predict(getVector(true, true, false, false, 2))
    println(s"nameTest: $nameTest")

    val bizTest = model.predict(getVector(false, false, false, false, 3))
    println(s"bizTest: $bizTest")
  }

  def getVector(fn: Boolean, ln: Boolean, title: Boolean, bizType: Boolean, cnt: Int): Vector = {
    val fnV = if (fn) 1.0 else 0.0
    val lnV = if (ln) 1.0 else 0.0
    val titleV = if (title) 1.0 else 0.0
    val bizTypeV = if (bizType) 1.0 else 0.0
    val cntV = if (cnt < 10) cnt / 10.0 else 1.0
    Vectors.dense(fnV, lnV, titleV, bizTypeV, cntV)
  }

  def getVector(s: String): Vector = {
    ???
  }

}
