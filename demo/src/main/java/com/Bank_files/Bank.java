package com.Bank_files;
import java.sql.Time;
import java.util.ArrayList;


public class Bank{
    static int ID;
    public static MessageInfo<ArrayList<Account>> getAccounts() {
        ArrayList<Account> accounts = DataHandling.getAllAccounts().data;
        if (accounts.isEmpty()) {
            return new MessageInfo<>(false, "No accounts found!");
        }
        return new MessageInfo<>(true, "Accounts retrieved successfully!", accounts);
    }
    public static MessageInfo<Account> createAccount(String account, String name, String password) {
        if (!account.endsWith("@bank.com")) {
            return new MessageInfo<>(false, "Account name must end with '@bank.com'!");
        }
        if (account.isEmpty() || name.isEmpty() || password.isEmpty()) {
            return new MessageInfo<>(false, "Account name, name, and password cannot be empty!");
        }
        if (DataHandling.getAccountByAccountName(account).success) {
            return new MessageInfo<>(false, "Account name already exists!");
        }
        MessageInfo<Account> passwordCheck = Utilities.passwordSecure(password);
        if (passwordCheck.success == false) {
            return passwordCheck;
        }
        ID++;
        Account user = new Account(account, name, ID, password);
        DataHandling.insertAccount(account, name, ID, password, user.get_balance(), user.history);
        return new MessageInfo<>(true, "Account created successfully!", user);
        }
    public static MessageInfo<Account> loginAccount(String account, String password) {
        MessageInfo<Account> accountInfo = DataHandling.getAccountByAccountName(account);
        if (!accountInfo.success) {
            return new MessageInfo<>(false, "Account not found!");
        }
        Account foundAccount = accountInfo.data;
        if (foundAccount.password.equals(password)) {
            return new MessageInfo<>(true, "Login successful!", foundAccount);
        } else {
            return new MessageInfo<>(false, "Wrong password!");
        }
    }
    public static MessageInfo<Account> loginAccount(String account) {
        MessageInfo<Account> accountInfo = DataHandling.getAccountByAccountName(account);
        if (!accountInfo.success) {
            return new MessageInfo<>(false, "Account not found!");
        }
        Account foundAccount = accountInfo.data;
        foundAccount.history.add("login at " + Time.valueOf(java.time.LocalTime.now()) + " date: " + java.time.LocalDate.now());
        DataHandling.saveAccountChanges(foundAccount);
        return new MessageInfo<>(true, "Login successful!", foundAccount);

    }
    public static ArrayList<String> showHistory(Account acc) {
        ArrayList<String> old_history = acc.history;
        acc.history.add("Checked history at " + Time.valueOf(java.time.LocalTime.now()) + " date: " + java.time.LocalDate.now());
        DataHandling.saveAccountChanges(acc);
        return old_history;
    }
    public static MessageInfo<Account> transfer(String targetAccount, Account acc, double amount) {
        double Balance = acc.get_balance();
        if (amount <= 0.0) {
            return new MessageInfo<>(false, "Transfer cancelled due to invalid input you can only enter positive numbers.");
        }
        if (amount > Balance) {
            return new MessageInfo<>(false, "insufficient funds");
        } else {
            MessageInfo<Account> targetAccountInfo = DataHandling.getAccountByAccountName(targetAccount);
            if (!targetAccountInfo.success) {
                return new MessageInfo<>(false, "Target account not found!");
            }
            Account targetAcc = targetAccountInfo.data;
            if (targetAcc.account.equals(acc.account)) {
                return new MessageInfo<>(false, "You cannot transfer to the same account!");
            }
            targetAcc.change_balance(amount);
            acc.change_balance(-amount);
            double newBalance = acc.get_balance();
            acc.history.add("Transferred: " + amount + " to " + targetAcc.account + " New Balance: " + newBalance + " at " + Time.valueOf(java.time.LocalTime.now()) + " date: " + java.time.LocalDate.now());
            targetAcc.history.add("Received: " + amount + " from " + acc.account + " New Balance: " + targetAcc.get_balance() + " at " + Time.valueOf(java.time.LocalTime.now()) + " date: " + java.time.LocalDate.now() );
            DataHandling.saveAccountChanges(acc);
            DataHandling.saveAccountChanges(targetAcc);
            return new MessageInfo<>(true, "Transfer successful! Your new balance is: " + newBalance);
        }
    }
    public static MessageInfo<Account> deposit(double amount, Account acc) {
        if (amount <= 0.0) {
            return new MessageInfo<>(false, "Deposit cancelled due to invalid input you can only enter positive numbers.");
        }
        acc.change_balance(amount);
        double newBalance = acc.get_balance();
        acc.history.add("Deposited: " + amount + " New Balance: " + newBalance + " at " + Time.valueOf(java.time.LocalTime.now()) + " date: " + java.time.LocalDate.now());
        DataHandling.saveAccountChanges(acc);
        return new MessageInfo<>(true, "Deposit successful! Your new balance is: " + newBalance);
    }
    public static MessageInfo<Account> withdraw(double amount, Account acc) {
        if (amount <= 0.0) {
            return new MessageInfo<>(false, "Withdrawal cancelled due to invalid input you can only enter positive numbers.");
        }
        double Balance = acc.get_balance();
        if (amount > Balance) {
            return new MessageInfo<>(false, "Insufficient funds!");
        } 
        acc.change_balance(-amount);
        double newBalance = acc.get_balance();
        acc.history.add("Withdrew: " + amount + " New Balance: " + newBalance + " at " + Time.valueOf(java.time.LocalTime.now()) + " date: " + java.time.LocalDate.now());
        DataHandling.saveAccountChanges(acc);
        return new MessageInfo<>(true, "Withdrawal successful! Your new balance is: " + newBalance);
    }
    public static double get_balance(Account acc) {
        return acc.get_balance();
    }
    public static double checkBalance(Account acc) {
        acc.history.add("Checked balance: " + acc.get_balance() + " at " + Time.valueOf(java.time.LocalTime.now()) + " date: " + java.time.LocalDate.now());
        DataHandling.saveAccountChanges(acc);
        return acc.get_balance();
    }
    public static MessageInfo<ArrayList<Void>> login(Account acc) {
        acc.history.add("login at " + Time.valueOf(java.time.LocalTime.now()) + " date: " + java.time.LocalDate.now());
        DataHandling.saveAccountChanges(acc);
        return new MessageInfo<>(true, "Login successful!");
    }
    public static MessageInfo<ArrayList<Void>> logout(Account acc) {
        acc.history.add("logout at " + Time.valueOf(java.time.LocalTime.now()) + " date: " + java.time.LocalDate.now());
        DataHandling.saveAccountChanges(acc);
        return new MessageInfo<>(true, "Logout successful!");
    } 
    public static MessageInfo<ArrayList<String>> getAllAccountsNames() {
        ArrayList<String> accountNames = DataHandling.getAllAccountsAccountNames().data;
        if (accountNames.isEmpty()) {
            return new MessageInfo<>(false, "No accounts found!");
        }
        return new MessageInfo<>(true, "Accounts retrieved successfully!", accountNames);
    }
}