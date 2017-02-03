package com.tms.ml.feature.ngram.training

import java.util.UUID

import com.tms.ml.dataTypes.Feature
import com.tms.ml.feature.ngram.NgramModel
import org.apache.spark.ml.Estimator
import org.apache.spark.ml.param.ParamMap
import org.apache.spark.ml.util.{DefaultParamsWritable, Identifiable}
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.types.StructType
import org.apache.spark.tms.ml.NgramSparkSupport


/**
  * Created by Nir.Laor on 1/23/2017.
  */
class NgramSparkTrainer(override val uid: String)
  extends Estimator[NgramSparkModel] with NgramSparkSupport with DefaultParamsWritable {


  def this() = this(Identifiable.randomUID("Ngram_"))

  /** create a new instance of BagOfWordsSparkFeaturesExtractor, overriding the current setting with the 'extra' parameters*/
  override def copy(extra: ParamMap): NgramSparkTrainer = defaultCopy(extra)

  override def fit(dataset: Dataset[_]): NgramSparkModel = {
    val features = Seq(Feature(0, "spark", 1.0), Feature(1, "nir", 1.0))
    new NgramSparkModel(uid, new NgramModel(features))
          .setInputCol(this.getInputCol)
          .setOutputCol(this.getOutputCol)
  }

  override def transformSchema(schema: StructType): StructType = validateAndTransformSchema(schema)
}
