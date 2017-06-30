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

# Flume Configs
* [flafka](flafka.conf) - reads from netcat source to kafka sink
* [netcat to hbase and hdfs](net2hbaseAndHdfs.conf) - reads from netcat source to HBase and hdfs (2 sinks)


# Spark
This task we used Scala as the spark application.

# Kafka
## Kafka brokers
"ip-172-31-38-164.us-west-2.compute.internal:9092,ip-172-31-37-179.us-west-2.compute.internal:9092,ip-172-31-42-170.us-west-2.compute.internal:9092"
## Topic
```
kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test
```

# HBase
2 tables
1 measurements only


# Kudu
Like HBase, can perform UPSERTS

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
