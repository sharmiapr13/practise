
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

public class KafkaConsumerApp {
    public static void main(String[] args)
    {
        Properties myconsumerprop = new Properties();

        myconsumerprop.put("bootstrap.servers", "localhost:9092, localhost:9093, localhost:9094");
        myconsumerprop.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        myconsumerprop.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        myconsumerprop.put("group.id", "devtest3");
        myconsumerprop.put("auto.offset.reset", "earliest");
        myconsumerprop.put("client.id", "testclient");
        KafkaConsumer myconsumer = new KafkaConsumer(myconsumerprop);

        ArrayList<String> mytopicList = new ArrayList<String>();

        mytopicList.add("devtesting");
        mytopicList.add("devtestouting");

        myconsumer.subscribe(mytopicList);
        try{
                while(true)
                {
                    ConsumerRecords<String,String> myConsumerRecords = myconsumer.poll(10);
                    for (ConsumerRecord<String, String> myConsumerRecord : myConsumerRecords)
                    {
                        System.out.println(String.format("Topic: %s, Partition: %d, Offset: %d, Key: %s, Value: %s",
                        myConsumerRecord.topic(), myConsumerRecord.partition()
                        , myConsumerRecord.offset(), myConsumerRecord.key(), myConsumerRecord.value()));
                    }

                }
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }finally {
            myconsumer.close();
        }

    }
}
