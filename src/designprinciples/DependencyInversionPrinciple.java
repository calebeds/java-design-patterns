package designprinciples;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

record Triplet<T, U, V>(T first, U second, V third) {
}

enum Relationship {
    PARENT,
    CHILD,
    SIBLING
}

class Person {
    private String name;

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

interface RelationshipBroser {
    List<Person> findAllChildrenOf(String name);
}

class Relationships implements RelationshipBroser {
    private final List<Triplet<Person, Relationship, Person>> relations = new ArrayList<>();

    public List<Triplet<Person, Relationship, Person>> getRelations() {
        return relations;
    }

    public void addParentAndChild(Person parent, Person child) {
        relations.add(new Triplet<>(parent, Relationship.PARENT, child));
        relations.add(new Triplet<>(child, Relationship.CHILD, parent));
    }

    @Override
    public List<Person> findAllChildrenOf(String name) {
        return relations.stream()
                .filter(personTriplet ->
                        personTriplet.first().getName().equals("John") && personTriplet.second().equals(Relationship.PARENT))
                .map(Triplet::third)
                .collect(Collectors.toList());
    }
}

class Research {
//    public Research(Relationships relationships) {
//        List<Triplet<Person, Relationship, Person>> relations = relationships.getRelations();
//        relations.stream()
//                .filter(personTriplet ->
//                        personTriplet.first().getName().equals("John") && personTriplet.second().equals(Relationship.PARENT))
//                .forEach(personTriplet ->
//                        System.out.println("John has a child called " + personTriplet.third().getName()));
//    }

    public Research(RelationshipBroser broser) {
        List<Person> children = broser.findAllChildrenOf("John");
        for(Person child: children) {
            System.out.println("John has a child called: " + child.getName());
        }
    }
}

public class DependencyInversionPrinciple {
    public static void main(String[] args) {
        final Person parent = new Person("John");
        final Person child1 = new Person("Chris");
        final Person child2 = new Person("Matt");

        Relationships relationships = new Relationships();
        relationships.addParentAndChild(parent, child1);
        relationships.addParentAndChild(parent, child2);

        new Research(relationships);
    }
}
