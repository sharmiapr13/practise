import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Arrays;
import java.util.Properties;

public class JSONArrayKafkaStream {
    public static void main(String[] args) throws ParseException {
        Properties streamProperties = new Properties();
        streamProperties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        streamProperties.put(StreamsConfig.APPLICATION_ID_CONFIG, "JSONKafkaStreamArray");
        streamProperties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        streamProperties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

        KStreamBuilder builder = new KStreamBuilder();
        final Serde<String> stringSerde = Serdes.String();

        KStream<String, String> JsonStream = builder.stream(stringSerde, stringSerde,"devpraba");
        KStream<String, String> transformedJson = JsonStream.flatMapValues( value ->
        {
            JSONObject data;
            String Message = "";
            try {
                JSONParser parser = new JSONParser();
                data = (JSONObject) parser.parse(value.toString());
                JSONArray node = (JSONArray) data.get("FO");

                for (int i = 0; i < node.size(); i++) {
                    JSONObject object = (JSONObject) node.get(i);
                    JSONArray olarr = (JSONArray) object.get("OrderLine");
                    for (int j = 0; j < olarr.size(); j++) {
                        JSONObject olobj = (JSONObject) olarr.get(j);
                        Message =  olobj.toString()+ " " + Message;
                    }
                }
            } catch (ParseException ex) {
                System.out.println(ex);
            }
            return Arrays.asList(Message);
        }).flatMapValues(value -> Arrays.asList(value.split(" ")));

        transformedJson.print();
        transformedJson.to("devprabaout");

        KafkaStreams kafkaStreams =new KafkaStreams(builder, streamProperties);
        kafkaStreams.start();
    }
}