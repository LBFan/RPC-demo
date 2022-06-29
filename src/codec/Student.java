package codec;

/**
 * @author : PF_23
 * @Description : TODO
 * @date : 2022/6/25 18:25.
 */

public class Student {
    String name;
    String value;

    public Student(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}

