package com.Bank_files;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.Bank_files.Account;
public class DataHandling {
    public static Connection getConnection() {
        // 1. The Address (URL)
        String url = "jdbc:postgresql://localhost:5432/Bank_db"; 
    
        // 2. The Credentials
        String user = "postgres"; 
        String pass = "Aswd@2026"; 

        try {
            // 3. The Handshake
            return DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            System.out.println("Connection failed! Check if Postgres is running.");
            e.printStackTrace();
            return null;
        }
    }
    public static void insertAccount(String account, String name, int id, String password, double balance, ArrayList<String> history) {
        String sql = "INSERT INTO accounts (account, name, id, password, balance, history) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, account);
            pstmt.setString(2, name);
            pstmt.setInt(3, id);
            pstmt.setString(4, password);
            pstmt.setDouble(5, balance);
            Object[] historyArray = history.toArray();
            pstmt.setArray(6, conn.createArrayOf("TEXT", historyArray));
            pstmt.executeUpdate();
            System.out.println("Account inserted successfully!");
        } catch (SQLException e) {
            System.out.println("Failed to insert account.");
            e.printStackTrace();
        }
    }
    public static MessageInfo<Account> getAccountByAccountName(String accountname) {
        String sql = "SELECT * FROM accounts WHERE account = ?";
    
    try (Connection conn = getConnection(); 
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setString(1, accountname);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            String name = rs.getString("name");
            double bal = rs.getDouble("balance");
            String password = rs.getString("password");
            int id = rs.getInt("id");
            ArrayList<String> historyArray = (ArrayList<String>) rs.getArray("history").getArray();
            System.out.println("Found: " + accountname + " | Name: " + name + " | Balance: $" + bal);
            
            return new MessageInfo<>(true, "Account retrieved successfully!", new Account(accountname, name, id, password, bal, historyArray));
        } else {
            System.out.println("User not found.");
            return new MessageInfo<>(false, "Account not found.", null);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    public static MessageInfo<ArrayList<Account>> returnAllAccounts() {
        String sql = "SELECT * FROM accounts";
        ArrayList<Account> accounts = new ArrayList<>();
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String account = rs.getString("account");
                String name = rs.getString("name");
                double bal = rs.getDouble("balance");
                String password = rs.getString("password");
                int id = rs.getInt("id");
                ArrayList<String> historyArray = (ArrayList<String>) rs.getArray("history").getArray();
                accounts.add(new Account(account, name, id, password, bal, historyArray));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts_names;
    }
    }
    public static void main(String[] args) {
        ArrayList<String> history = new ArrayList<>();
        history.add("Checked balance: 0.0");
        DataHandling.insertAccount("acc134@bank.com", "John Doe", 3, "password123", 0.0, history);
        DataHandling.getAccountByAccountName("acc134@bank.com");
        DataHandling.returnAllAccounts();
    }
}
