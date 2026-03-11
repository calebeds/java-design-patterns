package designpatterns.command;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class BankAccount {
    private int balance;
    private static final int OVERDRAFT_LIMIT = -500;

    public void deposit(int amount) {
        balance += amount;
        System.out.println("Deposited: " + amount + ", balance is now " + balance);
    }

    public boolean withdraw(int amount) {
        if(balance - amount >= OVERDRAFT_LIMIT) {
            balance -= amount;
            System.out.println("Withdrew: " + amount + ", balance is now " + balance);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "balance=" + balance +
                '}';
    }
}

interface Command  {
    void call();
    void undo();
}

class BankAccountCommand implements Command {
    private BankAccount account;
    private boolean succeeded;

    public enum Action {
        DEPOSIT, WITHDRAW
    }

    private Action action;
    private int amount;

    public BankAccountCommand(BankAccount account, Action action, int amount) {
        this.account = account;
        this.action = action;
        this.amount = amount;
    }

    @Override
    public void call() {
        switch (action) {
            case DEPOSIT -> {
                succeeded = true;
                account.deposit(amount);
            }
            case WITHDRAW -> succeeded = account.withdraw(amount);
        }
    }

    @Override
    public void undo() {
        if(!succeeded) {
            return;
        }
        switch (action) {
            case DEPOSIT -> account.withdraw(amount);
            case WITHDRAW -> account.deposit(amount);
        }
    }

}


public class CommandEx {
    public static void main(String[] args) {
        BankAccount bankAccount = new BankAccount();
        System.out.println(bankAccount);
        List<BankAccountCommand> commands = Arrays.asList(new BankAccountCommand(bankAccount, BankAccountCommand.Action.DEPOSIT, 100),
                new BankAccountCommand(bankAccount, BankAccountCommand.Action.WITHDRAW, 1000));

        for(Command command: commands) {
            command.call();
            System.out.println(bankAccount);
        }

        Collections.reverse(commands);

        for(Command command: commands) {
            command.undo();
            System.out.println(bankAccount);
        }
    }
}
