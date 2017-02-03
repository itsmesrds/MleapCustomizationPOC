package com.tms.ml.examples


import com.tms.ml.dataTypes.FeatureCustomType
import com.tms.ml.feature.ngram.runtime.NgramMleapOp
import ml.combust.bundle.BundleFile
import ml.combust.bundle.dsl.Bundle
import ml.combust.mleap.runtime.MleapContext
import ml.combust.mleap.runtime.transformer.Pipeline
import resource._

import scala.util.Success


/**
  * Created by Nir.Laor on 1/22/2017.
  */
object LoadMleapModel extends App{


  implicit val ctx = MleapContext.defaultContext.withCustomType(new FeatureCustomType)

  ctx.bundleRegistry.register(new FeatureCustomType)
  //ctx.withCustomType(new FeatureCustomType)
  ctx.bundleRegistry.register(new NgramMleapOp) // note that this is the runtime class op!

  val bundle = (for(bundleFile <- managed(BundleFile("file:///tmp/simple-spark-pipeline2.zip"))) yield {
    bundleFile.load()(ctx)
  }).opt.get




  println("here")
}
