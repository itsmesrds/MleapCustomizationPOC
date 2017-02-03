//package org.apache.spark.tms.ml
//
//import com.tms.ml.dataTypes.Feature
//import org.apache.spark.sql.catalyst.InternalRow
//import org.apache.spark.sql.types._
//
///**
//  * Created by Nir.Laor on 2/2/2017.
//  */
//class FeaturesArrayUDT extends UserDefinedType[Array[Feature]] {
//
//  override def sqlType: DataType = ArrayType(org.apache.spark.tms.ml.FeatureUDT, containsNull = false)
//
//  override def serialize(obj: Array[Feature]): Any ={
//    InternalRow.fromSeq(obj)
//  }
//
//  override def deserialize(datum: Any): Array[Feature] = {
////    datum match {
////      case row: InternalRow => {
////        row.toSeq(Seq[Feature])
////      }
////    }
//  }
//
//  override def userClass: Class[Array[Feature]] = classOf[Array[Feature]]
//}
