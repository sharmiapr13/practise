import com.google.gson.Gson;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.kafka.streams.kstream.ValueMapper;

import java.util.Properties;

public class JSONStringKafkaStream {
    public static void main(String[] args) throws Exception {
        Properties streamProperties = new Properties();
        streamProperties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        streamProperties.put(StreamsConfig.APPLICATION_ID_CONFIG, "JSONStrKafkaStreamAppl");
        streamProperties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.ByteArray().getClass().getName());
        streamProperties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

        KStreamBuilder builder = new KStreamBuilder();

        final Serde<byte[]> byteArraySerde = Serdes.ByteArray();
        final Serde<String> stringSerde = Serdes.String();

        KStream<byte[], String> JsonStream = builder.stream(byteArraySerde, stringSerde,"devjson");
        KStream<byte[], String> transformedJson = JsonStream.mapValues(new ValueMapper<String, String>() {
            @Override
            public String apply(String s) {
                Gson gson = new Gson();
                Person person = gson.fromJson(s, Person.class);
                return person.getName().toString();
            }
        });

                transformedJson.to("devjsonout");

        KafkaStreams kafkaStreams =new KafkaStreams(builder, streamProperties);
        kafkaStreams.start();
    }
}