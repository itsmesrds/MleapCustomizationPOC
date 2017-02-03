package com.tms.ml.feature

import org.apache.spark.ml.param.{Param, Params, StringArrayParam}

/**
  * Created by Nir.Laor on 1/23/2017.
  */
trait HasOutputCol extends Params {

  /**
    * Param for output column name.
    * @group param
    */
  final val outputCol: Param[String] = new Param[String](this, "outputCol", "output column name")

  setDefault(outputCol, uid + "__output")

  /** @group getParam */
  final def getOutputCol: String = $(outputCol)
}
