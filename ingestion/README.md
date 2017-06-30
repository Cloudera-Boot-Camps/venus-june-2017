# Ingestion
Use the AWS cluster that your group has been assigned

Ingest measurements and reference data from Oracle
500 million measurements, 8 detectors, 128 galaxies, 106 astrophysicists
Tables: measurements, detectors, galaxies, astrophysicists
Will provide Oracle connection string and credentials

Make the tables available to Impala for querying in Hue

## use case model
One record is one measurement

Each measurement is
* At a point in time
* Made up of three signal amplitudes
* From a gravitational wave detector somewhere in the world
* Being performed by an astrophysicist
* Listening to a nearby galaxy

A gravitational wave is detected when the first and third amplitudes are > 0.995, and the second amplitude is < 0.005

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