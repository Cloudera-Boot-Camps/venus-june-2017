hdfs dfs -rm -R /user/hue/oozie/workspaces/import-table
hdfs dfs -mkdir /user/hue/oozie/workspaces/import-table
hdfs dfs -mkdir /user/hue/oozie/workspaces/import-table/lib
hdfs dfs -put workflow.xml /user/hue/oozie/workspaces/import-table
hdfs dfs -put hive-site.xml /user/hue/oozie/workspaces/import-table/lib
hdfs dfs -put ojdbc6.jar /user/hue/oozie/workspaces/import-table/lib



    
