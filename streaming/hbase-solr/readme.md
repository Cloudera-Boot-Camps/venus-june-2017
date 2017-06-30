
## HBase to Solr, using Lily hbase NRT indexer

### pre-req
* Install key-value store indexer from CM
* Enable Hbase Replication


### Solr Setup

First, generate instance dir for solr collection
```
solrctl instancedir --generate $HOME/accessCollection
```
Edit the schema file (Sample in this git)
```
vi $HOME/accessCollection/conf/schema.xml 
```
Then run following commands to create collection
```
solrctl instancedir --create accessCollection $HOME/accessCollection
solrctl collection  --create accessCollection -s 1
```

If something goes wrong, run this to clean up
```
solrctl collection --deletedocs accessCollection
solrctl collection --delete accessCollection
solrctl instancedir --delete accessCollection
```

### HBase lily indexer

Modify morphline-hbase-mapper, and morphlines.conf file accordingly with collection name, and run indexer
```
hbase-indexer add-indexer --name hbaseIndexer --indexer-conf ./morphline-hbase-mapper.xml --connection-param solr.collection=accessCollection --connection-param solr.zk=$ZK:2181/solr
```

You can list or delete indexer as well
```
hbase-indexer list-indexers
hbase-indexer delete-indexer -name accessCollection
```
