package designpatterns.mediator;


import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;

import java.util.ArrayList;
import java.util.List;

// rx
class EventBroker extends Observable<Integer> {
    private List<Observer<? super Integer>> observers =
            new ArrayList<>();

    @Override
    protected void subscribeActual(@NonNull Observer<? super Integer> observer) {
        observers.add(observer);
    }

    public void publish(int n) {
        for(Observer<? super Integer> observer: observers) {
            observer.onNext(n);;
        }
    }
}

class FootballPlayer {
    private int goalsScored = 0;
    private EventBroker broker;
    private String name;

    public FootballPlayer(String name, EventBroker broker) {
        this.name = name;
        this.broker = broker;
    }

    public void score() {
        broker.publish(++goalsScored);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class FootballCoach {
    public FootballCoach(EventBroker broker) {
        broker.subscribe(integer -> System.out.println("Hey, you scored " + integer + " goals!"));
    }
}

public class ReactiveEventBroker {
    public static void main(String[] args) {
        EventBroker broker = new EventBroker();
        FootballPlayer jones = new FootballPlayer("jones", broker);
        FootballCoach footballCoach = new FootballCoach(broker);

        jones.score();
        jones.score();
        jones.score();
    }
}
