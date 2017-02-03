package org.apache.spark.tms.ml

import com.tms.ml.feature.{HasInputCol, HasOutputCol}
import org.apache.spark.ml.linalg.VectorUDT
import org.apache.spark.ml.param.Params
import org.apache.spark.ml.util.{Identifiable, SchemaUtils}
import org.apache.spark.sql.types.{ArrayType, StringType, StructField, StructType}

/**
  * Created by Nir.Laor on 1/24/2017.
  */
trait NgramSparkSupport extends FeaturesExtractorSparkSupport{


  /**
    * Validate and transform the input schema.
    */
  protected def validateAndTransformSchema(schema: StructType): StructType = {
    val inputType = schema($(inputCol)).dataType
    require(inputType.isInstanceOf[ArrayType],
      s"The input column must be ArrayType, but got $inputType.")
    SchemaUtils.appendColumn(schema, $(outputCol), new VectorUDT)
  }



}
