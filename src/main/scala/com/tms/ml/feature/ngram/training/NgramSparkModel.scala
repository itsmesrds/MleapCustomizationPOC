package com.tms.ml.feature.ngram.training

import com.tms.ml.dataTypes.Feature
import com.tms.ml.feature.ngram.NgramModel
import org.apache.spark.ml.Model
import org.apache.spark.ml.linalg.{SparseVector, Vector}
import org.apache.spark.ml.param.ParamMap
import org.apache.spark.ml.util.{MLWritable, MLWriter}
import org.apache.spark.sql.functions.{col, udf}
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.{DataFrame, Dataset}
import org.apache.spark.tms.ml.NgramSparkSupport

/**
  * A wrapper for BagOfWordsModel to Spark API
  * */

class NgramSparkModel(override val uid: String, val model: NgramModel)
  extends Model[NgramSparkModel]
    with NgramSparkSupport with MLWritable {


  override def copy(extra: ParamMap): NgramSparkModel = defaultCopy(extra)

  // todo: check if we want to output two columns (as is done in hashing TF) to get the features description for report,
  // todo: or to output array of features and have a utility to convert it to vector of doubles that will be used by all
  // todo: features extractors
  def featuresArrayToVector(in: Seq[Feature]): Vector ={
    val indices: Array[Int] = in.map{_.id}.toArray
    val values: Array[Double] = in.map{_.value}.toArray
    new SparseVector(model.numFeatures, indices, values)
  }

  override def transform(dataset: Dataset[_]): DataFrame = {
    transformSchema(dataset.schema, logging = true)
    // user defined function that wraps the model function
    val process = udf { tokens: Seq[String] => featuresArrayToVector(model.extractFeatures(tokens)) }
    dataset.withColumn($(outputCol), process(col($(inputCol))))
  }

  override def transformSchema(schema: StructType): StructType = validateAndTransformSchema(schema)

  override def write: MLWriter = {
    println("write")
    null
  }
}
