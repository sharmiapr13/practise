import java.util.ArrayList;
import java.util.List;

public class Person {

    private String name;
    private int age;
    private String location;
    private java.util.List<Object> phone = new ArrayList<Object>();

    public Person(String name, int age)
    {
        this.name = name;
        this.age = age;
    }

    public List<Object> getPhone() {
        return phone;
    }

    public void setName(String name)
    {
        this.name=name;
    }

    public void setAge(int age)
    {
        this.age=age;
    }

    public void setLocation(String location)
    {
        this.location=location;
    }

    public String getLocation() {
        return location;
    }

    public int getAge()
    {
        return age;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString()
    {
        return "Name:" +this.name +" Age:" +this.age +" Location:" +this.location;
    }
}