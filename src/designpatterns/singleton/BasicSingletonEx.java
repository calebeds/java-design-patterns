package designpatterns.singleton;

import java.io.*;

class BasicSingleton implements Serializable {
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

    protected Object readResolve() {
        return INSTANCE;
    }
}

public class BasicSingletonEx {

    static void saveToFile(BasicSingleton singleton, String filename) throws Exception {
        try(FileOutputStream fileOutputStream = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fileOutputStream)) {
            out.writeObject(singleton);
        }
    }

    static BasicSingleton readFromFile(String filename) throws Exception {
        try(FileInputStream fileInputStream = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fileInputStream)) {
            return (BasicSingleton) in.readObject();
        }
    }

    public static void main(String[] args) throws Exception {
        BasicSingleton singleton = BasicSingleton.getInstance();
        singleton.setValue(123);

        String filename = "singleton.bin";
        saveToFile(singleton, filename);

        singleton.setValue(222);

        BasicSingleton singleton2 = readFromFile(filename);

        System.out.println(singleton == singleton2);

        System.out.println(singleton.getValue());
        System.out.println(singleton2.getValue());
    }
}
