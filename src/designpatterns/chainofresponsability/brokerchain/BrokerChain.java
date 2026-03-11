package designpatterns.chainofresponsability.brokerchain;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

// CQS
class Event<Args> {
    private int index = 0;
    private final Map<Integer, Consumer<Args>> handlers = new HashMap<>();

    public int subscribe(Consumer<Args> handler) {
        int i = index;
        handlers.put(index++, handler);
        return i;
    }

    public void unsubscribe(int key) {
        handlers.remove(key);
    }

    public void fire(Args args) {
        for(Consumer<Args> handler: handlers.values()) {
            handler.accept(args);
        }
    }
}

class Query {
    private String creatureName;
    enum Argument {
        ATTACK, DEFENSE
    }
    private Argument argument;
    private int result;

    public Query(String creatureName, Argument argument, int result) {
        this.creatureName = creatureName;
        this.argument = argument;
        this.result = result;
    }

    public String getCreatureName() {
        return creatureName;
    }

    public void setCreatureName(String creatureName) {
        this.creatureName = creatureName;
    }

    public Argument getArgument() {
        return argument;
    }

    public void setArgument(Argument argument) {
        this.argument = argument;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}

class Game {
    private Event<Query> queries = new Event<>();

    public Event<Query> getQueries() {
        return queries;
    }

    public void setQueries(Event<Query> queries) {
        this.queries = queries;
    }
}

class Creature {
    private Game game;
    private String name;
    private int baseAttack, baseDefense;

    public Creature(Game game, String name, int baseAttack, int baseDefense) {
        this.game = game;
        this.name = name;
        this.baseAttack = baseAttack;
        this.baseDefense = baseDefense;
    }

    public Game getGame() {
        return game;
    }

    public String getName() {
        return name;
    }

    int getAttack() {
        Query query = new Query(name, Query.Argument.ATTACK, baseAttack);
        game.getQueries().fire(query);
        return query.getResult();
    }

    int getDefense() {
        Query query = new Query(name, Query.Argument.DEFENSE, baseDefense);
        game.getQueries().fire(query);
        return query.getResult();
    }

    public int getBaseAttack() {
        return baseAttack;
    }

    public int getBaseDefense() {
        return baseDefense;
    }

    @Override
    public String toString() {
        return "Creature{" +
                "game=" + game +
                ", name='" + name + '\'' +
                ", attack=" + getAttack() +
                ", defense=" + getDefense() +
                '}';
    }
}

class CreatureModifier {
    protected Game game;
    protected Creature creature;

    public CreatureModifier(Game game, Creature creature) {
        this.game = game;
        this.creature = creature;
    }
}

class IncreaseDefenseModifier extends CreatureModifier {

    public IncreaseDefenseModifier(Game game, Creature creature) {
        super(game, creature);

        game.getQueries().subscribe(query -> {
            if(query.getCreatureName().equals(creature.getName())
                    && query.getArgument() == Query.Argument.DEFENSE) {
                query.setResult(query.getResult() + 2);
            }
        });
    }
}

class DoubleAttackModifier extends CreatureModifier implements AutoCloseable {

    private final int token;

    public DoubleAttackModifier(Game game, Creature creature) {
        super(game, creature);

        token = game.getQueries().subscribe(query -> {
            if(query.getCreatureName().equals(creature.getName())
                    && query.getArgument() == Query.Argument.ATTACK) {
                query.setResult(query.getResult() * 2);
            }
        });
    }


    @Override
    public void close() {
        game.getQueries().unsubscribe(token);
    }
}

public class BrokerChain {
    public static void main(String[] args) {
        Game game = new Game();
        Creature strongGoblin = new Creature(game, "Strong Goblin", 2, 2);
        System.out.println(strongGoblin);

        IncreaseDefenseModifier increaseDefenseModifier = new IncreaseDefenseModifier(game, strongGoblin);

        try(DoubleAttackModifier doubleAttackModifier = new DoubleAttackModifier(game, strongGoblin)) {
            System.out.println(strongGoblin);
        }

        System.out.println(strongGoblin);
    }
}
