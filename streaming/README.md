# Stream processing
Run measurements stream using the provided data generator

* Build a “generator -> Flume -> HBase” pipeline
* Then switch out HBase for HDFS

* Build a “generator -> Flume -> Kafka -> Spark Streaming -> HBase” pipeline
* Then switch out HBase for Kudu
* First use built-in Spark-Kudu API, then use Envelope
* Then switch out Kudu for Solr

## Executing the generator from maven
```
$> export JAVA_HOME=/user/java/jdk1.7 ........
$> mvn exec:java \
-Dexec.mainClass="com.cloudera.fce.bootcamp.MeasurementGenerator" \
-Dexec.args="localhost 4444"
```

# Flume Config
We decided to create 1 config/agent to write to multiple sinks: hbase, kafka, hdfs
* [flume config](flume2.conf)


# Spark
This task we used Scala as the spark application. 

* [kafka direct to HBase](../spark/src/main/scala/com/cloudera/bootcamp/Streaming.scala) - this could be improved by performing a foreachPartition to better manage the connection by batching the inserts by partition
* [kafka direct to Kudu](../spark/src/main/scala/com/cloudera/bootcamp/Kudu.scala)
* [kafka direct to solr](hbase-solr)

# Kafka
## Kafka brokers
"ip-172-31-38-164.us-west-2.compute.internal:9092,ip-172-31-37-179.us-west-2.compute.internal:9092,ip-172-31-42-170.us-west-2.compute.internal:9092"
## Topic
```
kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test
```

# HBase
```
create 'measurements', 'cf'
```
```
scan 'measurements'
```
```
get 'measurements', '1111', 'cf:amplitude_1'
```

# Kudu
```
// Create an instance of a KuduContext
val kuduContext = new KuduContext("ip-172-31-40-237.us-west-2.compute.internal:7051")
kuduContext.insertRows(df, "measurements")
```

# shell
```
#!/bin/bash
spark-submit \
  --packages org.apache.kudu:kudu-spark_2.10:1.1.0 \
  --class com.cloudera.bootcamp.Kudu \
  --master yarn \
  --executor-cores 2 \
  --executor-memory 3g \
  spark_2.10-1.0.jar $@
```
