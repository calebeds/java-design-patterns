package designpatterns.singleton;

import com.google.common.collect.Iterables;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

interface Database {
    int getPopulation(String name);
}

class SingletonDatabase implements Database {
    private static final SingletonDatabase INSTANCE = new SingletonDatabase();

    private static int instanceCount = 0;

    private Dictionary<String, Integer> capitals = new Hashtable<>();

    private SingletonDatabase() {
        instanceCount++;
        System.out.println("Initializing database");

        try {
//            File file = new File(SingletonDatabase.class.getProtectionDomain().getCodeSource().getLocation().getPath());
//            Path fullPath = Paths.get(file.getPath(), "capitals.txt");
            Path fullPath = Paths.get("/home/calebe/develop/projects/java-design-patterns/res/capitals.txt");

            List<String> lines = Files.readAllLines(fullPath);

            Iterables.partition(lines, 2)
                    .forEach(kv -> capitals.put(kv.get(0).trim(), Integer.parseInt(kv.get(1))));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static int getCount() {
        return instanceCount;
    }

    public static SingletonDatabase getInstance() {
        return INSTANCE;
    }

    public int getPopulation(String name) {
        return capitals.get(name);
    }
}

class SingletonRecordFinder {
    public int getTotalPopulation(List<String> names) {
        int result = 0;
        for(String name: names) {
            result += SingletonDatabase.getInstance().getPopulation(name);
        }

        return result;
    }
}

class ConfigurableRecordFinder {
    private Database database;

    public ConfigurableRecordFinder(Database database) {
        this.database = database;
    }
    public int getTotalPopulation(List<String> names) {
        int result = 0;
        for(String name: names) {
            result += database.getPopulation(name);
        }

        return result;
    }

}

class DummyDatabase implements Database {

    private Dictionary<String, Integer> data = new Hashtable<>();

    public DummyDatabase() {
        this.data.put("alpha", 1);
        this.data.put("beta", 2);
        this.data.put("gamma", 3);
    }

    @Override
    public int getPopulation(String name) {
        return data.get(name);
    }
}

public class Testability {
    @Test
    public void singletonTotalPopulationTest() {
        SingletonRecordFinder rf = new SingletonRecordFinder();
        List<String> names = List.of("Seoul", "Mexico City");
        int totalPopulation = rf.getTotalPopulation(names);

        assertEquals(22490482+23016800, totalPopulation);
    }

    @Test
    public void dependentPopulationTest() {
        DummyDatabase db = new DummyDatabase();
        ConfigurableRecordFinder recordFinder = new ConfigurableRecordFinder(db);
        assertEquals(4, recordFinder.getTotalPopulation(List.of("alpha", "gama")));
    }
}
