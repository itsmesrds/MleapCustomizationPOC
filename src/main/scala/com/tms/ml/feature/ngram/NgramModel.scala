package com.tms.ml.feature.ngram

import com.tms.ml.dataTypes.Feature

/**
  * This class is the raw model of the feature.
  * It should NOT depend on Spark Dataframe/RDD or on mleap APIs
  *
  * The model is initialized (by the trainer of when loaded from serialization by the ModelOp class) with a
  * Seq of Feature objects. the model extracts a subset of the features for a given input in its 'extractFeatures' method.
  *
  *
  */

// todo: separate to three projects: base (containing APIs & TMS models w/o dependency in spark / mleap),
// todo: runtime (depends on mleap) and training (depends on the mleap one and on spark)
class NgramModel(val features: Seq[Feature]){

  // create a private map to ease the access on runtime. serialization of a Seq is easier than a Map.
  val ngramMap = features.map{f => f.description -> (f.id, f.value)}.toMap

  // dummy implementation
  def extractFeatures(doc: Seq[String]) : Seq[Feature] ={
    doc.collect{
      case token if ngramMap.contains(token) =>
        val (id, value) = ngramMap(token)
        Feature(id, token, value)
    }
  }

  lazy val numFeatures: Int = features.size

}






