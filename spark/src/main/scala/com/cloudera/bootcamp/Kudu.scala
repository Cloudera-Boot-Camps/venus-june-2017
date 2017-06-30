package com.cloudera.bootcamp

import kafka.serializer.StringDecoder
import org.apache.kudu.spark.kudu.KuduContext
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.kafka._
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.sql._
import org.apache.spark.sql.types._


/**
  * Created by hdulay on 6/29/17.
  */
object Kudu extends App {

  args.foreach(println)

  val conf = new SparkConf().setAppName("bootcamp streaming")
  val ssc = new StreamingContext(conf, Seconds(1))

  val kafkaParams = Map[String, String]("metadata.broker.list" -> "ip-172-31-38-164.us-west-2.compute.internal:9092,ip-172-31-37-179.us-west-2.compute.internal:9092,ip-172-31-42-170.us-west-2.compute.internal:9092")
  val topicsSet = Set("gravity")
  val stream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, topicsSet)

  val lines = stream.map(_._2)

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

  write2kudu(lines)

  ssc.start() // Start the computation
  ssc.awaitTermination() // Wait for the computation to terminate

  def write2kudu(stream: DStream[String]): Unit = {

    stream.foreachRDD((rdd, time) => {

      println("+++++++"+rdd.count())

      if (!rdd.isEmpty()) {

        val rowRdd = rdd.map(line => {

          val cells = line.split(",")
          val r = Seq(cells(0), cells(1).toInt, cells(2).toInt, cells(3).toInt, cells(4).toLong, cells(5).toDouble, cells(6).toDouble, cells(7).toDouble)
          Row.fromSeq(r)
        })

        val sc = SparkContext.getOrCreate(conf)
        val df = SQLContext.getOrCreate(sc).createDataFrame(rowRdd, schema)

        // Create an instance of a KuduContext
        val kuduContext = new KuduContext("ip-172-31-40-237.us-west-2.compute.internal:7051")
        kuduContext.insertRows(df, "measurements")
      }
    })

  }

}
