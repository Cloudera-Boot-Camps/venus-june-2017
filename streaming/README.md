# Stream processing
```
export JAVA_HOME=/user/java/jdk1.7 ........
mvn exec:java -Dexec.mainClass="com.cloudera.fce.bootcamp.MeasurementGenerator" -Dexec.args="ec2-35-167-49-130.us-west-2.compute.amazonaws.com 4444"
```

# Flume Config


# Kafka 
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
