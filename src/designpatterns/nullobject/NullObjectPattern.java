package designpatterns.nullobject;

interface Log {
    void info(String msg);
    void warn(String msg);
}

class ConsoleLog implements Log {

    @Override
    public void info(String msg) {
        System.out.println(msg);
    }

    @Override
    public void warn(String msg) {
        System.out.println("WARNING " + msg);
    }
}

class BankAccount {
    private final Log log;
    private int balance;

    public BankAccount(Log log) {
        this.log = log;
    }

    public void deposit(int amount) {
        balance += amount;

        log.info("Deposited " + amount);
    }
}

final class NullLog implements Log {

    @Override
    public void info(String msg) {

    }

    @Override
    public void warn(String msg) {

    }
}


public class NullObjectPattern {
    public static void main(String[] args) {
        Log consoleLog = new NullLog();
        BankAccount account = new BankAccount(consoleLog);

        account.deposit(100);
    }
}
