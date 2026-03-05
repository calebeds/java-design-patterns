package designpatterns.singleton;

import java.util.HashMap;

enum Subsystem {
    PRIMARY,
    AUXILIARY,
    FALLBACK
}

class Printer {
    private static HashMap<Subsystem, Printer> instances = new HashMap<>();
    private static int instanceCount = 0;

    private Printer() {
        instanceCount++;
        System.out.println("A total of " + instanceCount + " instances created so far.");
    }

    public static Printer get(Subsystem subsystem) {
        if(instances.containsKey(subsystem)) {
            return instances.get(subsystem);
        }

        Printer instance = new Printer();
        instances.put(subsystem, instance);
        return instance;
    }
}

public class Multiton {
    public static void main(String[] args) {
        Printer main = Printer.get(Subsystem.PRIMARY);
        Printer aux = Printer.get(Subsystem.AUXILIARY);
        Printer aux2 = Printer.get(Subsystem.AUXILIARY);


    }
}
