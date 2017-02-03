package org.apache.spark.tms.ml

import com.tms.ml.feature.{HasInputCol, HasOutputCol}
import org.apache.spark.ml.param.Params

/**
  * Base trait for features extractors wrapper to Spark env - provides utilities.
  */
trait FeaturesExtractorSparkSupport extends Params with HasInputCol with HasOutputCol{

  /** @group setParam */
  def setInputCol(value: String): this.type = set(inputCol, value)

  /** @group setParam */
  def setOutputCol(value: String): this.type = set(outputCol, value)

}
