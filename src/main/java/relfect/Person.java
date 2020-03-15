package relfect;

public class Person {

    private int age;
    public String name;

    public Person() {

    }

    public Person(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void ssss() {
        System.out.println("我是中国人");
    }

    public void show(String str) {
        System.out.println("我来自" + str);
    }

    public String toString() {
        return "Person [age=" + age + ", name=" + name + "]";
    }
}
