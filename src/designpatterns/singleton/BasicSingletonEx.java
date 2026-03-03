package designpatterns.singleton;

class BasicSingleton {
    private static final BasicSingleton INSTANCE = new BasicSingleton();

    private int value = 0;

    private BasicSingleton() {

    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static BasicSingleton getInstance() {
        return INSTANCE;
    }
}

public class BasicSingletonEx {
    public static void main(String[] args) {
        BasicSingleton singleton = BasicSingleton.getInstance();
        singleton.setValue(123);
        System.out.println(singleton.getValue());
    }
}
