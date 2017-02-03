package com.tms.ml.feature.ngram.runtime

import com.tms.ml.feature.ngram.NgramModel
import ml.combust.bundle.BundleContext
import ml.combust.bundle.dsl.{Node, Shape}
import ml.combust.bundle.op.{OpModel, OpNode}
import ml.combust.mleap.runtime.MleapContext

/**
  * Created by Nir.Laor on 1/26/2017.
  *
  * deserialization class for NgramMleapFeaturesExtractor: creates the runtime wrapper
  * of the model.
  */
class NgramMleapOp extends OpNode[MleapContext, NgramMleapFeaturesExtractor, NgramModel]{
  override val Model: OpModel[MleapContext, NgramModel] = new NgramModelMleapOp

  override val klazz: Class[NgramMleapFeaturesExtractor] = classOf[NgramMleapFeaturesExtractor]

  override def name(node: NgramMleapFeaturesExtractor): String = node.uid

  override def model(node: NgramMleapFeaturesExtractor): NgramModel = node.model

  override def shape(node: NgramMleapFeaturesExtractor): Shape = Shape().withStandardIO(node.inputCol, node.outputCol)

  override def load(node: Node, model: NgramModel)(implicit context: BundleContext[MleapContext]): NgramMleapFeaturesExtractor ={

    // note the different types of contexts here: Op class gets a BundleContext, the extractor gets a MleapContext
    // the default implicit MleapContext instance is not aware of FeatureCustomType, so we must specifically

    new NgramMleapFeaturesExtractor(uid = node.name,
      inputCol = node.shape.standardInput.name,
      outputCol = node.shape.standardOutput.name,
      model = model) (context.context)
  }
}
