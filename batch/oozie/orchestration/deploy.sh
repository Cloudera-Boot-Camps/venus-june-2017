hdfs dfs -rm -R /user/hue/oozie/workspaces/etl
hdfs dfs -mkdir /user/hue/oozie/workspaces/etl
hdfs dfs -mkdir /user/hue/oozie/workspaces/etl/lib
hdfs dfs -put workflow.xml /user/hue/oozie/workspaces/etl
hdfs dfs -put hive-site.xml /user/hue/oozie/workspaces/etl/lib



    
