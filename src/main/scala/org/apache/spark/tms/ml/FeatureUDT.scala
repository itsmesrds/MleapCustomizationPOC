//package org.apache.spark.tms.ml
//
//import com.tms.ml.dataTypes.Feature
//import org.apache.spark.sql.types.{DataType, ObjectType, StringType, UserDefinedType}
//
///**
//  * Created by Nir.Laor on 2/2/2017.
//  */
//class FeatureUDT extends UserDefinedType[Feature]{
//  override def sqlType: DataType = StringType
//
//  override def serialize(obj: Feature): Any = obj.toString
//
//  override def deserialize(datum: Any): Feature = {
//    datum match{
//      case s:String => Feature.fromString(s)
//    }
//  }
//
//  override def userClass: Class[Feature] = classOf[Feature]
//}
