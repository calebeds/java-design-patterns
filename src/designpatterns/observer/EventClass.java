package designpatterns.observer;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

class Event<TArgs> {
    private int count = 0;
    private Map<Integer, Consumer<TArgs>> handlers = new HashMap<>();

    public Subscription addHandler(Consumer<TArgs> handler) {
        int i = count;
        handlers.put(count++, handler);
        return new Subscription(this, i);
    }

    public void fire(TArgs args) {
        for(Consumer<TArgs> handler: handlers.values()) {
            handler.accept(args);
        }
    }

    class Subscription implements AutoCloseable {
        private Event<TArgs> event;
        private int id;

        public Subscription(Event<TArgs> event, int id) {
            this.event = event;
            this.id = id;
        }

        @Override
        public void close() throws Exception {
            event.handlers.remove(id);
        }
    }
}

class PropertyChangedEventArgsV2 {
    private Object source;
    private String propertyName;

    public PropertyChangedEventArgsV2(Object source, String propertyName) {
        this.source = source;
        this.propertyName = propertyName;
    }

    public Object getSource() {
        return source;
    }

    public void setSource(Object source) {
        this.source = source;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }
}

class PersonV2 {
    private Event<PropertyChangedEventArgsV2> propertyChanged = new Event<>();

    private int age;

    public Event<PropertyChangedEventArgsV2> getPropertyChanged() {
        return propertyChanged;
    }

    public void setPropertyChanged(Event<PropertyChangedEventArgsV2> propertyChanged) {
        this.propertyChanged = propertyChanged;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if(this.age == age) {
            return;
        }
        this.age = age;
        propertyChanged.fire(new PropertyChangedEventArgsV2(this, "age"));
    }
}

public class EventClass {
    public static void main(String[] args) throws Exception {
        PersonV2 personV2 = new PersonV2();
        var sub = personV2.getPropertyChanged().addHandler(propertyChangedEventArgsV2 -> {
            System.out.println("Person's " + propertyChangedEventArgsV2.getPropertyName() + " has changed");
        });
        personV2.setAge(17);
        personV2.setAge(18);
        sub.close();
        personV2.setAge(19);

    }
}
