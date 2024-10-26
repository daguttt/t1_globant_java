public class MyClassTemp {

    private String name;
    private String lastname;

    public MyClassTemp(String name, String lastname) {
        this.name = name;
        this.lastname = lastname;
    }

    @Override
    public String toString() {
        return "MyClassTemp{" +
                "name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}
