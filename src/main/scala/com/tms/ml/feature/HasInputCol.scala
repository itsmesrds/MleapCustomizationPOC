package com.tms.ml.feature

import org.apache.spark.ml.param.{Param, Params}

/**
  * Created by Nir.Laor on 1/23/2017.
  * A copy of Spark 'HasInputCol' due to their extra strong privacy.
  */
trait HasInputCol extends Params {

  /**
    * Param for input column name.
    * @group param
    */
  final val inputCol: Param[String] = new Param[String](this, "inputCol", "input column name")

  /** @group getParam */
  final def getInputCol: String = $(inputCol)
}