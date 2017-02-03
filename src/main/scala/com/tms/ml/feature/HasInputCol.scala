package com.tms.ml.feature

import org.apache.spark.ml.param.{Param, Params}

/**
  * Created by Nir.Laor on 1/23/2017.
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