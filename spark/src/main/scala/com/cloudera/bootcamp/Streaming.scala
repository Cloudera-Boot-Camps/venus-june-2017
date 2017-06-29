package com.cloudera.bootcamp

import kafka.serializer.StringDecoder
import org.apache.hadoop.hbase.TableName
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka._
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.hadoop.hbase.client._
import org.apache.hadoop.hbase.util.Bytes
import org.apache.spark.streaming.dstream.DStream


/**
  * Created by hdulay on 6/29/17.
  */
object Streaming extends App {

  args.foreach(println)

  val conf = new SparkConf().setAppName("bootcamp streaming")

  val ssc = new StreamingContext(conf, Seconds(1))

  val kafkaParams = Map[String, String]("metadata.broker.list" -> "ip-172-31-38-164.us-west-2.compute.internal:9092,ip-172-31-37-179.us-west-2.compute.internal:9092,ip-172-31-42-170.us-west-2.compute.internal:9092")
  val topicsSet = Set("gravity")
  val stream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, topicsSet)

  //  val topicMap = Map[String, Int]("gravity" -> 1)
  //  val stream = KafkaUtils.createStream(ssc, "ip-172-31-40-237.us-west-2.compute.internal:2181", "cloudera_mirrormaker", topicMap)

  val lines = stream.map(_._2)

  write2hbase(lines)

  ssc.start() // Start the computation
  ssc.awaitTermination() // Wait for the computation to terminate

  def write2hbase(stream: DStream[String]): Unit = {

    stream.foreachRDD((rdd, time) => {

      if (!rdd.isEmpty()) {

        rdd.foreach(line => {

          val con = ConnectionFactory.createConnection()
          try {
            println("???????????????????" + line)

            val table = con.getTable(TableName.valueOf("measurements"))
            val cells = line.split(",")
            val put = new Put(Bytes.toBytes(cells(0)))
            val cf = Bytes.toBytes("cf")
            put.addColumn(cf, Bytes.toBytes("detector_id"), Bytes.toBytes(cells(1)))
            put.addColumn(cf, Bytes.toBytes("galaxy_id"), Bytes.toBytes(cells(2)))
            put.addColumn(cf, Bytes.toBytes("astrophysicist_id"), Bytes.toBytes(cells(3)))
            put.addColumn(cf, Bytes.toBytes("measurement_time"), Bytes.toBytes(cells(4)))
            put.addColumn(cf, Bytes.toBytes("amplitude_1"), Bytes.toBytes(cells(5)))
            put.addColumn(cf, Bytes.toBytes("amplitude_2"), Bytes.toBytes(cells(6)))
            put.addColumn(cf, Bytes.toBytes("amplitude_3"), Bytes.toBytes(cells(7)))
            table.put(put)
          } catch {
            case e: Exception => println(e)
          }
          finally {
            con.close()
          }

        })
      }

    })

  }

}
