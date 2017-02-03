package com.tms.ml.feature.ngram.runtime

import com.tms.ml.feature.ngram.NgramModel
import ml.combust.bundle.BundleContext
import ml.combust.bundle.dsl._
import ml.combust.bundle.op.OpModel
import ml.combust.mleap.runtime.MleapContext

/**
  * Created by Nir.Laor on 1/26/2017.
  *
  * Serializer for the model class.
  */
class NgramModelMleapOp extends OpModel[MleapContext, NgramModel]{
  override val klazz: Class[NgramModel] = classOf[NgramModel]

  override def opName: String = Bundle.BuiltinOps.feature.ngram

  override def store(model: Model, obj: NgramModel)
                    (implicit context: BundleContext[MleapContext]): Model = {

    // example for using a list of custom objects: the model contains a Seq of Features that
    // it can extract. these features are stored in the 'model.json' file as a JSON array.
    // in order to serialize the Feature (a custom object), we have to define its CustomType (FeatureCustomType)
    model.withAttr("ngram_features", Value.customList(obj.features))
  }

  override def load(model: Model)
                   (implicit context: BundleContext[MleapContext]): NgramModel = {

    new NgramModel(features = model.value("ngram_features").getCustomList)
  }

}
