package designpatterns.chainofresponsability;

import java.util.zip.GZIPOutputStream;

class Creature {
    private String name;
    private int attack, defense;


    public Creature(String name, int attack, int defense) {
        this.name = name;
        this.attack = attack;
        this.defense = defense;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    @Override
    public String toString() {
        return "Creature{" +
                "name='" + name + '\'' +
                ", attack=" + attack +
                ", defense=" + defense +
                '}';
    }
}

class CreatureModifier {
    protected Creature creature;
    protected CreatureModifier next;

    public CreatureModifier(Creature creature) {
        this.creature = creature;
    }

    public void add(CreatureModifier creatureModifier) {
        if(next != null) {
            next.add(creatureModifier);
        } else {
            next = creatureModifier;
        }
    }

    public void handle() {
        if(next != null) {
            next.handle();
        }
    }
}

class DoubleAttackModifier extends CreatureModifier {

    public DoubleAttackModifier(Creature creature) {
        super(creature);
    }

    @Override
    public void handle() {
        System.out.println("Doubling " + creature.getName() + "'s attack");
        creature.setAttack(creature.getAttack() * 2);
        super.handle();
    }
}

class IncreaseDefenseModifier extends CreatureModifier {

    public IncreaseDefenseModifier(Creature creature) {
        super(creature);
    }

    @Override
    public void handle() {
        System.out.println("Increasing " + creature.getName() +"'s defense");
        creature.setDefense(creature.getDefense() + 3);
        super.handle();
    }
}

class NoBonusesModifier extends CreatureModifier {

    public NoBonusesModifier(Creature creature) {
        super(creature);
    }

    @Override
    public void handle() {
        // nothing
        System.out.println("No bonuses for you!");
    }
}

public class MethodChain {
    public static void main(String[] args) {
        Creature goblin = new Creature("Goblin", 2, 2);
        System.out.println(goblin);

        CreatureModifier root = new CreatureModifier(goblin);

        root.add(new NoBonusesModifier(goblin));

        System.out.println("Let's double goblin's attack...");
        root.add(new DoubleAttackModifier(goblin));

        System.out.println("Let's increase goblin's defense");
        root.add(new IncreaseDefenseModifier(goblin));

        root.handle();

        System.out.println(goblin);
    }
}
