import java.util

import org.apache.kudu.client._
import org.apache.kudu.spark.kudu.KuduContext
import org.apache.spark.SparkContext
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.types._


// Create a Spark and SQL context
val sc = new SparkContext()
val sqlContext = new SQLContext(sc)
val kuduContext = new KuduContext("ip-172-31-40-237.us-west-2.compute.internal:7051")

val schema = StructType(List(
  StructField("measurement_id", StringType, false),
  StructField("detector_id", IntegerType, false),
  StructField("galaxy_id", IntegerType, false),
  StructField("astrophysicist_id", IntegerType, false),
  StructField("measurement_time", LongType, false),
  StructField("amplitude_1", DoubleType, false),
  StructField("amplitude_2", DoubleType, false),
  StructField("amplitude_3", DoubleType, false)
))

val kuduPrimaryKey = Seq("measurement_id")

val pcol = new java.util.ArrayList[String]()
pcol add "measurement_id"

val kuduTableOptions = new CreateTableOptions()
kuduTableOptions.setRangePartitionColumns(pcol).setNumReplicas(3)

kuduContext.createTable(
  "measurements", schema, kuduPrimaryKey, kuduTableOptions)


val kuduOptions: Map[String, String] = Map(
  "kudu.table"  -> "measurements",
  "kudu.master" -> "ip-172-31-40-237.us-west-2.compute.internal:7051")


import org.apache.kudu.spark.kudu._
import org.apache.kudu.client._

sqlContext.read.options(kuduOptions).kudu.show


