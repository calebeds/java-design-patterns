package designpatterns.prototype;

class Address1 {
    private String streetAddress, city, country;

    public Address1(String streetAddress, String city, String country) {
        this.streetAddress = streetAddress;
        this.city = city;
        this.country = country;
    }

    public Address1 (Address1 other) {
        this(other.streetAddress, other.city, other.country);
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    @Override
    public String toString() {
        return "Address1{" +
                "streetAddres='" + streetAddress + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}

class Employee {
    private String name;
    private Address1 address1;

    public Employee(String name, Address1 address1) {
        this.name = name;
        this.address1 = address1;
    }

    public Employee(Employee other) {
        name = other.name;
        address1 = new Address1(other.address1);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address1 getAddress1() {
        return address1;
    }

    public void setAddress1(Address1 address1) {
        this.address1 = address1;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", address1=" + address1 +
                '}';
    }
}

public class CopyConstructors {
    public static void main(String[] args) {
        Employee john = new Employee("John", new Address1("123 London Road", "London", "UK"));

        Employee chris = new Employee(john);

        chris.setName("Chris");
        System.out.println(john);
        System.out.println(chris);
    }
}
