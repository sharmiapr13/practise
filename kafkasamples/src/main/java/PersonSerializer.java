
import com.google.gson.Gson;
import org.apache.kafka.common.serialization.Serializer;

import java.io.Closeable;
import java.nio.charset.Charset;
import java.util.Map;

public class PersonSerializer implements Closeable, AutoCloseable, Serializer<Person>{


    static private Gson gson = new Gson();
    private static final Charset charset = Charset.forName("UTF-8");

    @Override
    public void configure(Map<String, ?>map, boolean b){
    }

    @Override
    public byte[] serialize(String s, Person person){
        String value = gson.toJson(person);
        return  value.getBytes(charset);
    }

    @Override
    public void close(){
    }
}
