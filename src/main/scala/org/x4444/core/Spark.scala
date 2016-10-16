package org.x4444.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.reflect.ClassTag

/**
  * Spark
  */
object Spark {

  private var _sc: Option[SparkContext] = None

  var AppName = "learn-mllib"

  private def createContext(): Unit = {
    val conf = new SparkConf().setAppName(AppName)
    if (!conf.contains("spark.master")) conf.setMaster("local[*]")

    assignSc(new SparkContext(conf))
  }

  def assignSc(currentSc: SparkContext): Unit = {
    if (isScAssigned) throw new RuntimeException("Spark context already exists")

    _sc = Some(currentSc)
  }

  def sc: SparkContext = getSc()

  def emptyRDD[T: ClassTag]: RDD[T] = sc.emptyRDD[T]

  def getSc(): SparkContext = {
    if (!isScAssigned) createContext()
    _sc.get
  }

  def isScAssigned: Boolean = {
    _sc.nonEmpty
  }

  def stopContexts(): Unit = {
    _sc.foreach(_.stop())
    _sc = None
  }

}
