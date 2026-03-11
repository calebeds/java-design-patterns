package designpatterns.command;

import java.util.List;

class BankAccount {
    private int balance;
    private int overdraftLimit = -500;

    public void deposit(int amount) {
        balance += amount;
        System.out.println("Deposited: " + amount + ", balance is now " + balance);
    }

    public void withdraw(int amount) {
        if(balance - amount >= overdraftLimit) {
            balance -= amount;
            System.out.println("Withdrew: " + amount + ", balance is now " + balance);
        }
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
}

class BankAccountCommand implements Command {
    private BankAccount account;
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
            case DEPOSIT -> account.deposit(amount);
            case WITHDRAW -> account.withdraw(amount);
        }
    }

}


public class CommandEx {
    public static void main(String[] args) {
        BankAccount bankAccount = new BankAccount();
        System.out.println(bankAccount);
        List<BankAccountCommand> commands = List.of(new BankAccountCommand(bankAccount, BankAccountCommand.Action.DEPOSIT, 100),
                new BankAccountCommand(bankAccount, BankAccountCommand.Action.WITHDRAW, 1000));

        for(BankAccountCommand command: commands) {
            command.call();
            System.out.println(bankAccount);
        }
    }
}
