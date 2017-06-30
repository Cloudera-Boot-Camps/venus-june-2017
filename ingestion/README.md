# sqoop
Using the direct method took only 15min to ingest the measurements table. Sqooped the other tables similarly
```
sqoop import \
  --num-mappers 9 \
  --connect "jdbc:oracle:thin:@gravity.cghfmcr8k3ia.us-west-2.rds.amazonaws.com:15210:gravity" \
  --username=gravity \
  --password=bootcamp \
  --direct \
  --fetch-size 100000 \
  --table MEASUREMENTS \
  --hive-import \
  --create-hive-table \
  --hive-database gravity \
  --hive-overwrite \
  --hive-table measurements

```

# Gravitational wave detected was 56
Retrieving the records. We got 56. Transformation of the amplitudes from string to decimal was necessary to execute the query
```
select * from measurements_parq 
where 
  cast(amplitude_1 as decimal(20,16)) > 0.995 and 
  cast(amplitude_3 as decimal(20,16)) > 0.995 and 
  cast(amplitude_2 as decimal(20,16)) < 0.005

```