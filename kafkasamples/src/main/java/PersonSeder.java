import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class PersonSeder implements Serde<Person>{

    private PersonSerializer serializer = new PersonSerializer();
    private PersonDeserializer deserializer = new PersonDeserializer();

    @Override
    public void configure(Map<String ,?> map, boolean b){
        serializer.configure(map, b);
        deserializer.configure(map, b);
    }

    @Override
    public Serializer<Person> serializer(){
        return serializer;
    }

    @Override
    public Deserializer<Person> deserializer(){
        return deserializer;
    }

    @Override
    public void close(){
        serializer.close();
        deserializer.close();
    }


}
