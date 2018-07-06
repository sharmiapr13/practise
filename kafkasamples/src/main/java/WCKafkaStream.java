import java.util.Properties;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.kafka.streams.kstream.KStream;
import java.util.Arrays;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;

public class WCKafkaStream {

    public static void main(String[] args) throws Exception {
        Properties streamsConfiguration = new Properties();
        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "wordcount-example");
        streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
       streamsConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
       streamsConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

        final Serde<String> stringSerde = Serdes.String();
        final Serde<Long> longSerde = Serdes.Long();

        // In the subsequent lines we define the processing topology of the Streams application.
        KStreamBuilder builder = new KStreamBuilder();

        // Construct a `KStream` from the input topic "TextLinesTopic", where message values
        // represent lines of text (for the sake of this example, we ignore whatever may be stored
        // in the message keys).
        //
        // Note: We could also just call `builder.stream("TextLinesTopic")` if we wanted to leverage
        // the default serdes specified in the Streams configuration above, because these defaults
        // match what's in the actual topic.  However we explicitly set the deserializers in the
        // call to `stream()` below in order to show how that's done, too.
        KStream<String, String> textLines = builder.stream(stringSerde, stringSerde, "devtopic");

        textLines.print();
        KStream<String, Long> wordCounts = textLines
                // Split each text line, by whitespace, into words.  The text lines are the record
                // values, i.e. we can ignore whatever data is in the record keys and thus invoke
                // `flatMapValues` instead of the more generic `flatMap`.
                .flatMapValues(value -> Arrays.asList(value.toLowerCase().split("\\W+")))
                // We will subsequently invoke `countByKey` to count the occurrences of words, so we use
                // `map` to ensure the key of each record contains the respective word.
                .map((key, word) -> new KeyValue<>(word, word)).groupByKey()
                .count("Count").toStream();
                // Count the occurrences of each word (record key).
                //
                // This will change the stream type from `KStream<String, String>` to
                // `KTable<String, Long>` (word -> count).  We must provide a name for
                // the resulting KTable, which will be used to name e.g. its associated
                // state store and changelog topic.
                //.countByKey("Counts")
                // Convert the `KTable<String, Long>` into a `KStream<String, Long>`.
                //.toStream();

        wordCounts.print();

        // Write the `KStream<String, Long>` to the output topic.
        //wordCounts.through(stringSerde, longSerde, "devtopicout");
        wordCounts.to(stringSerde, longSerde,"topicout");

        KStream<String, Long> outStream =  builder.stream(stringSerde, longSerde, "topicout");

        outStream.print();

        // Now that we have finished the definition of the processing topology we can actually run
        // it via `start()`.  The Streams application as a whole can be launched just like any
        // normal Java application that has a `main()` method.
        KafkaStreams streams = new KafkaStreams(builder, streamsConfiguration);
        streams.start();
    }

}