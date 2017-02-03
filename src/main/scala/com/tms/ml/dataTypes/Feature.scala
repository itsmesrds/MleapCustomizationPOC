package com.tms.ml.dataTypes

/**
  * Created by Nir.Laor on 1/26/2017.
  * A Feature instance represents:
  * 1. A feature that a Model can extract. the model is initialized with a set of features - either the output of a
  *    training process or loading from serialized file, and it can extract a subset of these features for a given input.
  * 2. A feature extracted for an input.
  *
  */
case class Feature(id: Int, description: String, value: Double) extends Serializable{
}

