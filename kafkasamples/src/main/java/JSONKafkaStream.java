import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;

import java.util.Properties;

public class JSONKafkaStream {
    public static void main(String[] args) throws Exception{
        Properties streamProperties = new Properties();
        streamProperties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        streamProperties.put(StreamsConfig.APPLICATION_ID_CONFIG, "JSONKafkaStreamApplication");
        streamProperties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.ByteArray().getClass().getName());
        streamProperties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, PersonSeder.class);

        KStreamBuilder builder = new KStreamBuilder();

        final Serde<byte[]> byteArraySerde = Serdes.ByteArray();

        KStream<byte[], Person> JsonStream = builder.stream("devjson");
        KStream<byte[], Person> transformedJson = JsonStream.map((key,person) -> {
            person.setLocation("testLocation");
            return KeyValue.pair(key, person);
        });
        transformedJson.to("devjsonout");

        KafkaStreams kafkaStreams =new KafkaStreams(builder, streamProperties);
        kafkaStreams.start();
    }
}
