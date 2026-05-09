package com.Bank_files;
import java.util.ArrayList;
public class Account{
    public String account; public String name; public int ID ;private double balance; String password; ArrayList<String> history;
    Account(String accountstr, String namestr, int IDnum, String password){
        this.account = accountstr;
        this.name = namestr;
        this.ID = IDnum;
        this.password = password;
        this.balance = 0.0;
        this.history = new ArrayList<>();
    }
    Account(String accountstr, String namestr, int IDnum, String password, double balance, ArrayList<String> history){
        this.account = accountstr;
        this.name = namestr;
        this.ID = IDnum;
        this.password = password;
        this.balance = balance;
        this.history = history;
    }
    void change_balance(double change){
        this.balance = this.balance + change;
    }
    double get_balance(){
        return this.balance;
    }
    
}