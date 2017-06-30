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


# Spark Properties To Consider

## spark.yarn.executor.memoryOverhead
executorMemory * 0.10, with minimum of 384	The amount of off-heap memory (in megabytes) to be allocated per executor. 

This is memory that accounts for things like VM overheads, interned strings, other native overheads, etc. This tends to grow with the executor size (typically 6-10%).-memory-overhead

## spark.executor.cores or (--executor-cores)
1 in YARN mode, all the available cores on the worker in standalone mode. Default

The number of cores to use on each executor. In standalone and Mesos coarse-grained modes, setting this parameter allows an application to run multiple executors on the same worker, provided that there are enough cores on that worker. Otherwise, only one executor per application will run on each worker.

## spark.executor.memory or (--executor-memory)
1g	default

Amount of memory to use per executor process (e.g. 2g, 8g).