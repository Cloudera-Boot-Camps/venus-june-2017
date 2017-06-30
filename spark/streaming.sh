#!/bin/bash
spark-submit \
  --packages org.apache.kudu:kudu-spark_2.10:1.1.0 \
  --class com.cloudera.bootcamp.Kudu \
  --master yarn \
  --executor-cores 2 \
  --executor-memory 3g \
  spark_2.10-1.0.jar $@