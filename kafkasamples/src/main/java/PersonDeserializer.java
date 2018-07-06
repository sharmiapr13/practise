import com.google.gson.Gson;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.Closeable;
import java.nio.charset.Charset;
import java.util.Map;

public class PersonDeserializer implements Closeable,AutoCloseable, Deserializer<Person>{

    static private Gson gson = new Gson();
    private static final Charset charset = Charset.forName("UTF-8");

    @Override
    public void configure(Map<String,?> map, boolean b) {
    }

    @Override
    public Person deserialize(String topic, byte[] bytes) {
        try{
            String person = new String(bytes, charset);
            return gson.fromJson(person, Person.class);
        }catch (Exception ex){
            throw new IllegalArgumentException("Error reading String", ex);
        }
    }

    @Override
    public void close(){
    }
}
