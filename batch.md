# Batch processing

# pipeline
sqoop -> 
hdfs/hive (raw) -> 
Hive
    compress
    pull out day from timestamp
    parquet ->

Final Table hive-transform 
    datatype conversion decimal
    join of all ref tables
    parquet
    compress
    dynamic partition on day -> 

# sqoop
```
sqoop import \
  --num-mappers 9 \
  --connect "jdbc:oracle:thin:@gravity.cghfmcr8k3ia.us-west-2.rds.amazonaws.com:15210:gravity" \
  --username=gravity \
  --password=bootcamp \
  --direct \
  --fetch-size 10000 \
  --table MEASUREMENTS \
  --hive-import \
  --create-hive-table \
  --hive-database gravity2 \
  --hive-overwrite \
  --hive-table measurements_raw
```

## sqoop test
```
sqoop import \
  --num-mappers 9 \
  --connect "jdbc:oracle:thin:@gravity.cghfmcr8k3ia.us-west-2.rds.amazonaws.com:15210:gravity" \
  --username=gravity \
  --password=bootcamp \
  --direct \
  --fetch-size 10000 \
  --query "select * from measurements WHERE \$CONDITIONS " \
  --split-by galaxy_id \
  --boundary-query "select 1, 128 from dual" \
  --hive-import \
  --create-hive-table \
  --hive-database gravity2 \
  --hive-overwrite \
  --hive-table measurements_100 \
  --target-dir /user/gravity2.measurements_100


```

# sbt for spark
```
name := "spark"

version := "1.0"

scalaVersion := "2.10.6"

resolvers += "Cloudera" at "https://repository.cloudera.com/artifactory/cloudera-repos"

// spark
//libraryDependencies += "org.apache.spark" % "spark-core_2.10" % "2.1.0"

// https://mvnrepository.com/artifact/org.apache.spark/spark-core_2.10
libraryDependencies += "org.apache.spark" % "spark-core_2.11" % "2.1.0.cloudera1"


// https://mvnrepository.com/artifact/org.apache.spark/spark-sql_2.11
libraryDependencies += "org.apache.spark" % "spark-sql_2.11" % "2.1.0.cloudera1"

// streaming
libraryDependencies += "org.apache.spark" % "spark-streaming_2.11" % "2.1.0.cloudera1"

// kudu
//libraryDependencies += "org.apache.kudu" % "kudu-client" % "1.2.0-cdh5.10.0"

// spark databricks csv reader
// libraryDependencies += "com.databricks" % "spark-csv_2.10" % "1.5.0"

// https://mvnrepository.com/artifact/org.apache.avro/avro
//libraryDependencies += "org.apache.avro" % "avro" % "1.8.1"

// simple csv writer
//libraryDependencies += "com.github.tototoshi" %% "scala-csv" % "1.3.4"

// scala test
libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.1"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"
resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases"
addSbtPlugin("com.artima.supersafe" % "sbtplugin" % "1.1.2")
        
```



