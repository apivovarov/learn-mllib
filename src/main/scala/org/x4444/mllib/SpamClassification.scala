package org.x4444.mllib

import org.apache.spark.SparkContext
import org.apache.spark.mllib.classification.LogisticRegressionWithSGD
import org.apache.spark.mllib.feature.HashingTF
import org.apache.spark.mllib.regression.LabeledPoint
import org.x4444.core.Spark

/**
  * SpamClassification
  */
object SpamClassification {

  def usage(): Unit = {
    println("SpamClassification <spam_path> <ham_path>")
  }

  def main(args: Array[String]): Unit = {

    if (args.size != 2) {
      usage()
      throw new RuntimeException("wrong usage")
    }

    val sc: SparkContext = Spark.getSc

    val spamFile = args(0)
    val hamFile = args(1)

    val spam = sc.textFile(spamFile)
    val ham = sc.textFile(hamFile)

    // Create a HashingTF instance to map email text to vectors of 10,000 features.
    val tf = new HashingTF(numFeatures = 10000)
    // Each email is split into words, and each word is mapped to one feature.
    val spamFeatures = spam.map(email => tf.transform(email.split(" ")))
    val normalFeatures = ham.map(email => tf.transform(email.split(" ")))

    // Create LabeledPoint datasets for positive (spam) and negative (normal) examples.
    val positiveExamples = spamFeatures.map(features => LabeledPoint(1, features))
    val negativeExamples = normalFeatures.map(features => LabeledPoint(0, features))
    val trainingData = positiveExamples.union(negativeExamples)
    trainingData.cache() // Cache since Logistic Regression is an iterative algorithm.

    // Run Logistic Regression using the SGD algorithm.
    val model = new LogisticRegressionWithSGD().run(trainingData)

    // Test on a positive example (spam) and a negative one (normal).
    val posTest = tf.transform("O M G GET cheap stuff by sending money to ...".split(" "))
    val negTest = tf.transform("Hi Dad, I started studying Spark the other ...".split(" "))
    println(s"Prediction for positive test example: ${model.predict(posTest)}")
    println(s"Prediction for negative test example: ${model.predict(negTest)}")
  }
}
