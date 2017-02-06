package com.tms.ml.examples


import com.tms.ml.dataTypes.FeatureCustomType
import com.tms.ml.feature.ngram.runtime.NgramMleapOp
import ml.combust.bundle.BundleFile
import ml.combust.bundle.dsl.Bundle
import ml.combust.mleap.runtime.MleapContext
import ml.combust.mleap.runtime.transformer.{Pipeline, Transformer}
import resource._



/**
  * Created by Nir.Laor on 1/22/2017.
  */
object LoadMleapModel extends App{


  implicit val ctx = MleapContext.defaultContext.withCustomType(new FeatureCustomType)

  ctx.bundleRegistry.register(new FeatureCustomType)
  //ctx.withCustomType(new FeatureCustomType)
  ctx.bundleRegistry.register(new NgramMleapOp) // note that this is the runtime class op!

  val bundle = (for(bundleFile <- managed(BundleFile("file:///tmp/simple-spark-pipeline2.zip"))) yield {
    bundleFile.load()(ctx).get
  }).opt.get.asInstanceOf[Bundle[Transformer]] // why do we have to cast here?


  // create a simple LeapFrame to transform
  import ml.combust.mleap.runtime.{Row, LeapFrame, LocalDataset}
  import ml.combust.mleap.runtime.types._

  val schema = StructType(StructField("text", StringType())).get
  val data = LocalDataset(Row("hello spark"),
                          Row("hello MLeap"))
  val frame = LeapFrame(schema, data)

  // transform the dataframe using our pipeline
  val mleapPipeline = bundle.root
  val frame2 = mleapPipeline.transform(frame).get
  val data2 = frame2.dataset


  assert(data2(0)(7) == 1.0) // "hello spark" score = 1.0
  assert(data2(1)(7) == 0.0) // "hello MLeap" score = 0.0

}
