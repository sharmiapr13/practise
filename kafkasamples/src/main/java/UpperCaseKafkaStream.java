import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;

import java.util.Properties;


public class UpperCaseKafkaStream {
    public static void main(String[] args) throws Exception
    {
        Properties streamProperties = new Properties();
        streamProperties.put(StreamsConfig.APPLICATION_ID_CONFIG, "TestUpperCaseApplication");
        streamProperties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        streamProperties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.ByteArray().getClass().getName());
        streamProperties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

        final Serde<String> stringSerde = Serdes.String();
        final Serde<byte[]> byteArraySerde = Serdes.ByteArray();

        KStreamBuilder streamBuilder = new KStreamBuilder();

        KStream<byte[], String> textLines = streamBuilder.stream(byteArraySerde, stringSerde, "TextLinesTopic");

        KStream<byte[], String>  upperCasedWithMapValues = textLines.mapValues(String::toUpperCase);

        upperCasedWithMapValues.to("WordsWithCountsTopic");

        KafkaStreams streams  = new KafkaStreams(streamBuilder, streamProperties);
        streams.start();
    }
}
