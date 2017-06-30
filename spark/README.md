# Scala Spark application
This spark application ingests the smaller tables:

* astrophysicists
* galaxies
* detectors

The larger table (measurements) was ingested by sqoop. We create dataframes directly
for the smaller tables directly from Oracle and transform the datatypes and
join them together with the measurements table by broadcasting the smaller tables.

There is also an equivalent python spark script [here](../batch/batch.py)

# Spark streaming sources
* [HBase](src/main/scala/com/cloudera/bootcamp/Streaming.scala)
* [Kudu](src/main/scala/com/cloudera/bootcamp/Kudu.scala)
