import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import java.util.Properties;

public class ConcatStringKafkaStream {
    public static void main(String[] args) {
        Properties streamProperties = new Properties();
        streamProperties.put(StreamsConfig.APPLICATION_ID_CONFIG, "StringKafkaStreamapplication");
        streamProperties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        streamProperties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.ByteArray().getClass().getName());
        streamProperties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        KStreamBuilder builder = new KStreamBuilder();

        final Serde<String> stringSerde = Serdes.String();
        final Serde<byte[]> byteArraySerde = Serdes.ByteArray();

        KStream<byte[], String> JsonStream = builder.stream(byteArraySerde, stringSerde, "devtesting");

        KStream<byte[],String> transformedJsonStream = JsonStream.mapValues(new ValueMapper<String, String>() {
            @Override
            public String apply(String s) {
                return s.concat(" with stream concat ");
            }
        });
         transformedJsonStream.to("devtestouting");

        KafkaStreams kafkaStreams = new KafkaStreams(builder, streamProperties);
        kafkaStreams.start();
    }

}
