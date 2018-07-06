
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.Properties;

public class KafkaProducerApp {
    public static void main(String[] args)
    {
        //create a properties directory for the required/optional producer config settings:
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092, localhost:9093, localhost:9094");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String,String> myProducer = new KafkaProducer<String, String>(props);

        try{
            for (int  i =0; i<10; i++)
            {
                myProducer.send(new ProducerRecord<String, String>("devtesting", Integer.toString(i),"My Message:" +Integer.toString(i)));
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }finally {
            myProducer.close();
        }
    }
}
