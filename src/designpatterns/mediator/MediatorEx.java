package designpatterns.mediator;

import java.util.ArrayList;
import java.util.List;

class Person {
    private String name;
    private ChatRoom room;
    private List<String> chatLog = new ArrayList<>();

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ChatRoom getRoom() {
        return room;
    }

    public void setRoom(ChatRoom room) {
        this.room = room;
    }

    public List<String> getChatLog() {
        return chatLog;
    }

    public void setChatLog(List<String> chatLog) {
        this.chatLog = chatLog;
    }

    public void receive(String sender, String message) {
        String string = sender + ": '" + message + "'";
        System.out.println("[" + name + "'s chat session] "  + string);
        chatLog.add(string);
    }

    public void say(String message) {
        room.broadcast(name, message);
    }

    public void privateMessage(String who, String message) {
        room.message(name, who, message);
    }
}

class ChatRoom {
    private List<Person> people = new ArrayList<>();

    public void join(Person person) {
        String joinMsg = person.getName() + " join the room";
        broadcast("room", joinMsg);

        person.setRoom(this);
        people.add(person);
    }

    public void broadcast(String source, String message) {
        for(Person person: people) {
            if(!person.getName().equals(source)) {
                person.receive(source, message);
            }
        }
    }

    public void message(String source, String destination, String message) {
        people.stream()
                .filter(person -> person.getName().equals(destination))
                .findFirst()
                .ifPresent(person -> person.receive(source, message));
    }
}

public class MediatorEx {
    public static void main(String[] args) {
        ChatRoom room = new ChatRoom();
        Person john = new Person("John");
        Person jane = new Person("Jane");

        room.join(john);
        room.join(jane);

        john.say("hi room");
        jane.say("oh, hey john");

        Person simon = new Person("Simon");
        room.join(simon);
        simon.say("hi everyone!");

        jane.privateMessage("Simon", "glad you could join us!");
    }
}

