## Imports
import os, sys
import operator
from pyspark import SparkConf, SparkContext
from pyspark.sql import SQLContext, HiveContext
from pyspark.sql.functions import udf, broadcast
from pyspark.sql.types import *


## Constants
APP_NAME = "pyspark-gravitational-wave"
JDBC = "jdbc"
ORA_URL = "jdbc:oracle:thin:@gravity.cghfmcr8k3ia.us-west-2.rds.amazonaws.com:15210:gravity"
ORA_DRIVER = "oracle.jdbc.driver.OracleDriver"
USER = "gravity"
PASSWORD = "bootcamp"
tables = ["GALAXIES", "DETECTORS", "ASTROPHYSICISTS"]


## Functions

def load(hc):
    gal_df = hc.read.format(JDBC).options(url=ORA_URL,driver=ORA_DRIVER,dbtable=tables[0],user=USER,password=PASSWORD).load()
    det_df = hc.read.format(JDBC).options(url=ORA_URL,driver=ORA_DRIVER,dbtable=tables[1],user=USER,password=PASSWORD).load()
    ast_df = hc.read.format(JDBC).options(url=ORA_URL,driver=ORA_DRIVER,dbtable=tables[2],user=USER,password=PASSWORD).load()
    m_df = hc.sql("SELECT * FROM gravity.measurements_parq");

    dfs = [m_df, gal_df, det_df, ast_df]
    return dfs;

def main():
    sqlc = SQLContext(sc)
    hc = HiveContext(sc)

    # LOAD Tables
    dfs = load(hc)

   # Add Flag
    flagged=dfs[0].withColumn('flag', (dfs[0].amplitude_1 > 0.995) & (dfs[0].amplitude_3 > 0.995) & (dfs[0].amplitude_2 < 0.005) )

    # Join Tables
    joined_table = flagged.join(broadcast(dfs[1]), ["galaxy_id"]).join(broadcast(dfs[2]), ["detector_id"]).join(broadcast(dfs[3]),["astrophysicist_id"])

    # Write results to Hive Tables
    dfs[1].write.option("compression","snappy").mode("overwrite").saveAsTable("gravity2.galaxies")
    dfs[2].write.option("compression","snappy").mode("overwrite").saveAsTable("gravity2.detectors")
    dfs[3].write.option("compression","snappy").mode("overwrite").saveAsTable("gravity2.astrophysicists")
    joined_table.write.option("compression","snappy").mode("overwrite").saveAsTable("gravity2.measurements_joined")



if __name__ == "__main__":
    # Configure OPTIONS
    conf = SparkConf().setAppName(APP_NAME)
    conf = conf.setMaster("yarn-client")
    sc   = SparkContext(conf=conf)

   # Execute Main functionality
    main()
