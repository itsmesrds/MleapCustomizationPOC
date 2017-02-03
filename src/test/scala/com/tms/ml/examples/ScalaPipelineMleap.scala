package com.tms.ml.examples

/**
  * Created by Nir.Laor on 1/19/2017.
  */

// $example on$
import ml.combust.mleap.runtime.transformer.{Pipeline => MleapPipelone}
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.ml.feature.{HashingTF, IDF, Tokenizer}
import ml.combust.mleap.runtime.MleapContext.defaultContext
import ml.combust.mleap.runtime.MleapSupport._
import ml.combust.mleap.spark.SparkSupport._
import java.io.File

import com.tms.ml.dataTypes.FeatureCustomType
import com.tms.ml.feature.ngram.training.{NgramMleapOp, NgramSparkTrainer}
import ml.combust.bundle.{BundleFile, BundleRegistry}
import ml.combust.bundle.serializer.SerializationFormat
import ml.combust.mleap.runtime.MleapContext
import ml.combust.mleap.spark.SparkSupport._
import org.apache.spark.ml.bundle.SparkBundleContext
import resource._
import org.apache.spark.ml.{Pipeline, Transformer}
import org.apache.spark.ml.mleap.SparkUtil
// $example off$
import org.apache.spark.sql.SparkSession

object ScalaPipelineMleap {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder
      .master("local")
      .appName("PipelineExample")
      .getOrCreate()

    // $example on$
    // Prepare training documents from a list of (id, text, label) tuples.
    val training = spark.createDataFrame(Seq(
      (0L, "a b c d e spark", 1.0),
      (1L, "b d", 0.0),
      (2L, "spark f g h", 1.0),
      (3L, "hadoop mapreduce", 0.0)
    )).toDF("id", "text", "label")

    // Configure an ML pipeline, which consists of three stages: tokenizer, hashingTF, and lr.
    val tokenizer = new Tokenizer()
      .setInputCol("text")
      .setOutputCol("words")

    val hashingTF = new HashingTF()
      .setNumFeatures(1000)
      .setInputCol(tokenizer.getOutputCol)
      .setOutputCol("tf-features")

    val idf = new IDF()
      .setInputCol(hashingTF.getOutputCol)
      .setOutputCol("idf-features")

    val ngram = new NgramSparkTrainer()
      .setInputCol("words")
      .setOutputCol("features")

    // todo: LR model works on "features" column: find how to concatenate all the features from all FEs to this column.
    val lr = new LogisticRegression()
      .setMaxIter(10)
      .setRegParam(0.001)


    // mleap pipeline does not have fit() - so we have to create a Spark Pipeline, fit is and take the fitted stages out of it
    val pipeline = new Pipeline()
      .setStages(Array(tokenizer, hashingTF, idf, ngram, lr))
    val fittedStages = pipeline.fit(training).stages

    // register custom Op classes for custom features extractors
    SparkBundleContext.defaultContext.bundleRegistry.register(new FeatureCustomType)
    SparkBundleContext.defaultContext.bundleRegistry.register(new NgramMleapOp)

    // create mleap pipeline
    val mleapPipeline = SparkUtil.createPipelineModel("2" , fittedStages)

    // store the pipeline
    for(modelFile <- managed(BundleFile("file:///tmp/simple-spark-pipeline2.zip"))) {
      mleapPipeline.writeBundle.
        format(SerializationFormat.Json).
        // delete the file if it already exists
        // overwrite. ????
        // name our pipeline
        name("simple-pipeline").
        // save our pipeline to a zip file
        // we can save a file to any supported java.nio.FileSystem
        save(modelFile)
    }


    // We can also save this unfit pipeline to disk
//    pipeline.write.overwrite().save("/tmp/unfit-lr-model")
//
//    // And load it back in during production
//    val sameModel = PipelineModel.load("/tmp/spark-logistic-regression-model")

    // Prepare test documents, which are unlabeled (id, text) tuples.
//    val test = spark.createDataFrame(Seq(
//      (4L, "spark i j k"),
//      (5L, "l m n"),
//      (6L, "spark hadoop spark"),
//      (7L, "apache hadoop")
//    )).toDF("id", "text")
//
//    // Make predictions on test documents.
//    model.transform(test)
//      .select("id", "text", "probability", "prediction")
//      .collect()
//      .foreach { case Row(id: Long, text: String, prob: Vector, prediction: Double) =>
//        println(s"($id, $text) --> prob=$prob, prediction=$prediction")
//      }
//    // $example off$

    spark.stop()
  }
}
// scalastyle:on println
