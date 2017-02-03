package com.tms.ml.feature.ngram.training

import com.tms.ml.feature.ngram.NgramModel
import com.tms.ml.feature.ngram.runtime.NgramModelMleapOp
import ml.combust.bundle.BundleContext
import ml.combust.bundle.dsl._
import ml.combust.bundle.op.{OpModel, OpNode}
import ml.combust.mleap.runtime.MleapContext

/**
  * this OpNode is used in training time to serialize the NgramModel and
  * its Spark wrapper (NgramSparkModel)
  */
class NgramMleapOp extends OpNode[MleapContext, NgramSparkModel, NgramModel]{

  override val Model: OpModel[MleapContext, NgramModel] = new NgramModelMleapOp

  override val klazz: Class[NgramSparkModel] = classOf[NgramSparkModel]

  override def name(node: NgramSparkModel): String = node.uid

  override def model(node: NgramSparkModel): NgramModel = node.model

  override def shape(node: NgramSparkModel): Shape = Shape().withStandardIO(node.getInputCol, node.getOutputCol)

  override def load(node: Node, model: NgramModel)(implicit context: BundleContext[MleapContext]): NgramSparkModel ={
    new NgramSparkModel(uid = node.name, model = model)
        .setInputCol(node.shape.standardInput.name)
        .setOutputCol(node.shape.standardOutput.name)
  }
}
