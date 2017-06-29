# sqoop
```



sqoop import-all-tables \
  --connect "jdbc:oracle:thin:@gravity.cghfmcr8k3ia.us-west-2.rds.amazonaws.com:15210:gravity" \
  --username gravity \
  --password bootcamp \
  --create-hive-table \
  --as-parquetfile \
  --
   --hive-import \
  --hive-database gravity \


  sqoop import \
  --connect "jdbc:oracle:thin:@gravity.cghfmcr8k3ia.us-west-2.rds.amazonaws.com:15210:gravity" \
  --username gravity \
  --password bootcamp \
  --create-hive-table \
  --as-parquetfile \
  --
  --hive-import \
  --hive-database gravity \

```


# insert into select
```
insert into measurements select * from

measurement_id      	string              	                    
detector_id         	int                 	                    
astrophysicist_id   	int                 	                    
measurement_time    	bigint              	                    
amplitude_1         	decimal(20,20)      	                    
amplitude_2         	decimal(20,20)      	                    
amplitude_3         	decimal(20,20)      	                    
galaxy_id           	int   
```

# not sure
```
--set hive.execution.engine=spark;
set hive.exec.max.dynamic.partitions.pernode=150;
set hive.exec.dynamic.partition.mode=nonstrict;
insert overwrite table gravity.measurements partition(galaxy_id)
select measurement_id, detector_id, astrophysicist_id, measurement_time, amplitude_1, amplitude_2, amplitude_3, galaxy_id from gravity.measurements_raw;
```

# sqoop eval call
```
sqoop eval --connect "jdbc:oracle:thin:@gravity.cghfmcr8k3ia.us-west-2.rds.amazonaws.com:15210:gravity"   --username=gravity   --password=bootcamp  -e 'selec* from measurements where 1=2'

sqoop eval \
--connect "jdbc:oracle:thin:@gravity.cghfmcr8k3ia.us-west-2.rds.amazonaws.com:15210:gravity" \
--username=gravity \
--password=bootcamp \
-e 'select GALAXY_ID, count(GALAXY_ID) from measurements group by GALAXY_ID'
```



# other
```

  create table measurements_d
(measurement_id string,
 galaxy_id int,
 astrophysicist_id int,
 measurement_time bigint,
 amplitude_1 decimal(20,20),
 amplitude_2 decimal(20,20),
 amplitude_3 decimal(20,20)
) 
partitioned by (detector_id int)
stored as PARQUET;

sqoop import 
  --connect "jdbc:oracle:thin:@gravity.cghfmcr8k3ia.us-west-2.rds.amazonaws.com:15210:gravity" \
  --username gravity \
  --password bootcamp \
  --create-hive-table \
  --as-parquetfile \



  sqoop import \
  --num-mappers 10 \
  --connect "jdbc:oracle:thin:@gravity.cghfmcr8k3ia.us-west-2.rds.amazonaws.com:15210:gravity" \
  --username=gravity \
  --password=bootcamp \
  --table measurements \
  --split-by detector_id \
  --hive-partition-key detector_id \  
 --hive-import \
  --hive-database gravity \
  --hive-table astrophysicists_raw \
  --target-dir "hdfs://ip-172-31-40-237.us-west-2.compute.internal:8020/user/hive/warehouse/gravity.db/astrophysicists_raw"
  ```