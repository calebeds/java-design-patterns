package designpatterns.observer;

import java.util.ArrayList;
import java.util.List;

class PropertyChangedEventArgs<T> {
    private T source;
    private String propertyName;
    private Object newValue;

    public PropertyChangedEventArgs(T source, String propertyName, Object newValue) {
        this.source = source;
        this.propertyName = propertyName;
        this.newValue = newValue;
    }

    public T getSource() {
        return source;
    }

    public void setSource(T source) {
        this.source = source;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public Object getNewValue() {
        return newValue;
    }

    public void setNewValue(Object newValue) {
        this.newValue = newValue;
    }


}

interface Observer<T> {
    void handle(PropertyChangedEventArgs<T> args);
}

class Observable<T> {
    private List<Observer<T>> observers = new ArrayList<>();

    public void subscribe(Observer<T> observer) {
        observers.add(observer);
    }

    protected void propertyChanged(T source, String propertyName, Object newValue) {
        for(Observer<T> observer: observers) {
            observer.handle(new PropertyChangedEventArgs<>(source, propertyName, newValue));
        }
    }
}

public class Person extends Observable<Person>{
    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if(this.age == age) {
            return;
        }
        this.age = age;
        propertyChanged(this, "age", age);
    }
}

class Demo implements Observer<Person> {

    public static void main(String[] args) {
        Demo demo = new Demo();
    }

    public Demo() {
        Person person = new Person();
        person.subscribe(this);
        for(int i = 20; i < 24; ++i) {
            person.setAge(i);
        }
    }

    @Override
    public void handle(PropertyChangedEventArgs<Person> args) {
        System.out.println("Person's " + args.getPropertyName() + " has changed to " + args.getNewValue());
    }
}
