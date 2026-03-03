package designpatterns.prototype;

import java.util.Arrays;

class Address implements Cloneable {
    private String streetName;
    private int houseNumber;

    public Address(String streetName, int houseNumber) {
        this.streetName = streetName;
        this.houseNumber = houseNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    @Override
    public String toString() {
        return "Address{" +
                "streetName='" + streetName + '\'' +
                ", houseNumber=" + houseNumber +
                '}';
    }

    @Override
    public Address clone() {
        return new Address(streetName, houseNumber);
    }
}

class Person implements Cloneable {
    private String[] names;
    private Address address;

    public Person(String[] names, Address address) {
        this.names = names;
        this.address = address;
    }

    public String[] getNames() {
        return names;
    }

    public void setNames(String[] names) {
        this.names = names;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Person{" +
                "names=" + Arrays.toString(names) +
                ", address=" + address +
                '}';
    }

    @Override
    public Person clone() throws CloneNotSupportedException {
        return new Person(names.clone(), address.clone());
    }
}

public class CloneableEx {
    public static void main(String[] args) throws CloneNotSupportedException {
        Person john = new Person(new String[]{"John", "Smith"}, new Address("London Road", 123));

        Person jane = john.clone();
        jane.getNames()[0] = "Jane";
        jane.getAddress().setHouseNumber(124);

        System.out.println(john);
        System.out.println(jane);

    }
}
