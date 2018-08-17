Kafka Basics:

~/kafka_2.12-0.10.2.0/bin
./zookeeper-server-start.sh ../config/zookeeper.properties
./kafka-server-start.sh ../config/server.properties

./kafka-topics.sh --zookeeper localhost:2181 --create --topic placeorder --partitions 1 --replication-factor 1
./kafka-topics.sh --list --zookeeper localhost:2181
./kafka-topics.sh  --zookeeper localhost:2181 --describe --topic devtest

./kafka-console-producer.sh --broker-list localhost:9092 --topic devtest
./kafka-console-consumer.sh  --bootstrap-server localhost:9092 --from-beginning --topic devtest
