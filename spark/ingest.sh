spark-submit \
  --class com.cloudera.bootcamp.Main \
  --master yarn \
  --executor-cores 2 \
  --executor-memory 3g \
  spark_2.10-1.0.jar $@
