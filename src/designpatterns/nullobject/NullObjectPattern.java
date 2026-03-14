package designpatterns.nullobject;

import java.lang.reflect.Proxy;

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

    @SuppressWarnings("unchecked")
    static <T> T noOp(Class<T> interf) {
        return (T) Proxy.newProxyInstance(interf.getClassLoader(),
                new Class<?>[]{ interf },
                (proxy, method, args) -> {
                  if(method.getReturnType().equals(Void.TYPE)) {
                      return null;
                  } else {
                      return method.getReturnType().getConstructor().newInstance();
                  }
                }
        );
    }

    public static void main(String[] args) {
//        Log consoleLog = new NullLog();
         Log log = noOp(Log.class);
        BankAccount account = new BankAccount(log);

        account.deposit(100);
    }
}
