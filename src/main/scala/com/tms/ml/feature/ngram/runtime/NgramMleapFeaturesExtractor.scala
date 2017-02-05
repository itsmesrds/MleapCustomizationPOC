package com.tms.ml.feature.ngram.runtime

import com.tms.ml.feature.ngram.NgramModel
import ml.combust.mleap.runtime.MleapContext
import ml.combust.mleap.runtime.function.UserDefinedFunction
import ml.combust.mleap.runtime.transformer.{FeatureTransformer, Transformer}
import org.apache.spark.ml.linalg.Vectors
import ml.combust.mleap.tensor.Tensor
import ml.combust.mleap.runtime.converter.VectorConverters._


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
  // the model extracts Features, which are designed to be more human-readable (than the features extracted by Sparks'
  // provided features extractors), for future Reports module.
  // The features are converted to a Seq of (index, value) in order to create a Vector
  override val exec: UserDefinedFunction = (value: Seq[String]) => extractFeatures(value): Tensor[Double]

  def extractFeatures(value: Seq[String]): Tensor[Double] ={
    val features = model.extractFeatures(value).map{f=> (f.id, f.value)}
    Vectors.sparse(model.numFeatures, features)
  }

}
