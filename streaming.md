# Stream processing
```
export JAVA_HOME=/user/java/jdk1.7 ........
mvn exec:java -Dexec.mainClass="com.cloudera.fce.bootcamp.MeasurementGenerator" -Dexec.args="ec2-35-167-49-130.us-west-2.compute.amazonaws.com 4444"
```

# Flume Config


# Kafka 

## Topic
```
kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test
```

# HBase
2 tables
1 measurements only


# Kudu
Like HBase, can perform UPSERTS

