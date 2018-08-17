Kafka Basics:

1. Download the Kafka Binary from below link.

        https://kafka.apache.org/downloads

2. Untar the files
       
       tar -xzf kafka_*.tgz

3. Go to the Bin folder
      
        cd ~/kafka_*/bin

4. Start the zookeeper

        ./zookeeper-server-start.sh ../config/zookeeper.properties &

5. Start the Kafka Broker

        ./kafka-server-start.sh ../config/server.properties &

6. Create a topic

        ./kafka-topics.sh --zookeeper localhost:2181 --create --topic testtopic --partitions 1 --replication-factor 1

7. List the topics created 

        ./kafka-topics.sh --list --zookeeper localhost:2181

8. Display topic information

        ./kafka-topics.sh  --zookeeper localhost:2181 --describe --topic testtopic

9. Produce message to the topic
        
        ./kafka-console-producer.sh --broker-list localhost:9092 --topic testtopic

10. Consume the message from the topic

        ./kafka-console-consumer.sh  --bootstrap-server localhost:9092 --from-beginning --topic testtopic

11. Produce message with Key

        ./kafka-console-producer.sh --broker-list localhost:9092, localhost:9093 --topic testtopic --property parse.key=true --property key.separator=,

12. Consume message with Key

        ./kafka-console-consumer.sh --bootstrap-server localhost:9092, localhost:9093 --topic testtopic --property print.key=true --property key.separator=, --from-beginning 

