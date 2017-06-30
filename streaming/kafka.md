## Some Kafka commands

### Setup environment
```
export ZK=ip-172-31-49-255.ec2.internal:2181,ip-172-31-49-41.ec2.internal:2181,ip-172-31-48-197.ec2.internal:2181
export KBROKERS=ip-172-31-50-218.ec2.internal:9092,ip-172-31-54-31.ec2.internal:9092,ip-172-31-49-41.ec2.internal:9092
export KTOPIC=gravity
```
### Create the kafka topic
```
kafka-topics --zookeeper $ZK --delete --topic $KTOPIC
kafka-topics --zookeeper $ZK --create --topic $KTOPIC --partitions 10 --replication-factor 2
kafka-topics --zookeeper $ZK --describe --topic $KTOPIC
```

### Producer
```
kafka-console-producer --broker-list $KBROKERS --topic $KTOPIC
```

### Consumer
```
kafka-console-consumer --zookeeper $ZK --topic $KTOPIC
```
