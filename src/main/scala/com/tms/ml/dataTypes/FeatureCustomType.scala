package com.tms.ml.dataTypes

import spray.json._
import DefaultJsonProtocol._
import ml.combust.bundle.custom.CustomType
import spray.json.DefaultJsonProtocol.jsonFormat3
import spray.json.{JsValue, RootJsonFormat}

/**
  * Created by Nir.Laor on 2/2/2017.
  * spray-json formatter for Feature class. required for implementing mleap CustomType
  */
object FeatureFormat extends RootJsonFormat[Feature] {

  implicit val featureFormat = jsonFormat3(Feature.apply)

  override def write(obj: Feature): JsValue = obj.toJson

  override def read(json: JsValue): Feature = json.convertTo[Feature]
}

/**
  * CustomType wrapper for Feature type: required for serialization/deserialization
  * of Features in the ModelOp class.
  * */
class FeatureCustomType extends CustomType[Feature]{
  override val klazz: Class[Feature] = classOf[Feature]

  // the class name contains a '$' sign which should be removed in order to make the
  //
  override def name: String = {
    val nameWithDollar = Feature.getClass.getName
    val dollarIdx = nameWithDollar.indexOf('$')
    if (dollarIdx > -1) nameWithDollar.substring(0, dollarIdx) else nameWithDollar
  }

  override def format: RootJsonFormat[Feature] =  FeatureFormat
}
