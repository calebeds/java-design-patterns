package designpatterns.builder;

class Person {
    private String name;
    private String position;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", position='" + position + '\'' +
                '}';
    }
}

class PersonBuilder<SELF extends PersonBuilder<SELF>> {
    protected Person person = new Person();

    public SELF withName(String name) {
        person.setName(name);
        return self();
    }

    public Person build() {
        return person;
    }

    protected SELF self() {
        return (SELF) this;
    }
}

class EmployeeBuilder extends PersonBuilder<EmployeeBuilder> {
     public EmployeeBuilder worksAt(String position) {
         person.setPosition(position);
         return self();
     }

    @Override
    protected EmployeeBuilder self() {
        return this;
    }
}

public class FluentBuilder {
    public static void main(String[] args) {
        EmployeeBuilder personBuilder = new EmployeeBuilder();
        Person calebe = personBuilder.withName("Calebe")
                .worksAt("Developer")
                .build();

        System.out.println(calebe);
    }
}
