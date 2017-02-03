package com.tms.ml.feature.ngram.runtime

import com.tms.ml.feature.ngram.NgramModel
import ml.combust.mleap.runtime.MleapContext
import ml.combust.mleap.runtime.function.UserDefinedFunction
import ml.combust.mleap.runtime.transformer.{FeatureTransformer, Transformer}
import ml.combust.mleap.tensor.SparseTensor


/**
  * Created by Nir.Laor on 1/26/2017.
  *
  * Wraps the model (an NgramModel) with mleap runtime API (FeatureTransformer).
  * Created by NgramMleapOp when the pipeline is loaded from serialization.
  */
class NgramMleapFeaturesExtractor(override val uid: String = Transformer.uniqueName("ngram"),
                                  override val inputCol: String,
                                  override val outputCol: String,
                                  val model: NgramModel) (implicit context: MleapContext) extends FeatureTransformer {

  // important note:
  // this val is created in the class <init>. the context passed to the constructor should be aware
  // of FeatureCustomType in order to properly create the class.
  //
  // the model extracts Features, which are desinged to be more human-readable (than the features exctracted by Sparks'
  // provided features extractors).
  // todo: convert the features Seq to vector of their values (what type of vector?)
  override val exec: UserDefinedFunction = (value: Seq[String]) => {
    val features = model.extractFeatures(value)
    val indices = features.map{_.id}
    val values = features.map{_.value}.toArray

    SparseTensor[Double](indices =  Seq(indices),
      values = values,
      dimensions = Seq(1))
  }

}
