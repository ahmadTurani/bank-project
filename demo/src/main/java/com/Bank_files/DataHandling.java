package com.Bank_files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
            System.out.println("Connection failed: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    public static MessageInfo<Void> deleteAllAccounts() {
        String sql = "DELETE FROM accounts";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
            return new MessageInfo<>(true, "All accounts deleted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            return new MessageInfo<>(false, "Error occurred while deleting accounts.");
        }
    }
    public static MessageInfo<Void> deleteAccount(String account){
        String sql = "DELETE FROM accounts WHERE account = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, account);
            pstmt.executeUpdate();
            return new MessageInfo<>(true, "Account deleted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            return new MessageInfo<>(false, "Error occurred while deleting account.");
        }
    }
    public static MessageInfo<Account> insertAccount(String account, String name, int id, String password, double balance, ArrayList<String> history) {
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
            return new MessageInfo<>(true, "Account inserted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            return new MessageInfo<>(false, "Database error occurred while inserting account." + e.getMessage());
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
                double balance = rs.getDouble("balance");
                int id = rs.getInt("id");
                String password = rs.getString("password");
                java.sql.Array sqlHistoryArray = rs.getArray("history");
                String[] rawHistoryArray = (String[]) sqlHistoryArray.getArray();
                ArrayList<String> historyArray = new ArrayList<>();
                for (String entry : rawHistoryArray) {
                historyArray.add(entry);
                }
                return new MessageInfo<>(true, "Account found!", new Account(accountname, name, id, password, balance, historyArray));
            } else {
                return new MessageInfo<>(false, "Account not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new MessageInfo<>(false, "Database error occurred while retrieving account." + e.getMessage());
        }
    }
    public static MessageInfo<ArrayList<String>> getAllAccountsAccountNames() {
        String sql = "SELECT * FROM accounts";
        ArrayList<String> accounts = new ArrayList<>();
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String accountname = rs.getString("account");
                accounts.add(accountname);
            }
            return new MessageInfo<>(true, "Accounts retrieved successfully!", accounts);
        } catch (SQLException e) {
            e.printStackTrace();
            return new MessageInfo<>(false, "Database error occurred while retrieving accounts." + e.getMessage());
        }
    }
    public static MessageInfo<ArrayList<Account>> getAllAccounts() {
        String sql = "SELECT * FROM accounts";
        ArrayList<Account> accounts = new ArrayList<>();
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String accountname = rs.getString("account");
                String name = rs.getString("name");
                double balance = rs.getDouble("balance");
                int id = rs.getInt("id");
                String password = rs.getString("password");
                java.sql.Array sqlHistoryArray = rs.getArray("history");
                String[] rawHistoryArray = (String[]) sqlHistoryArray.getArray();
                ArrayList<String> historyArray = new ArrayList<>();
                for (String entry : rawHistoryArray) {
                    historyArray.add(entry);
                }
                accounts.add(new Account(accountname, name, id, password, balance, historyArray));
            }
            return new MessageInfo<>(true, "Accounts retrieved successfully!", accounts);
        } catch (SQLException e) {
            e.printStackTrace();
            return new MessageInfo<>(false, "Database error occurred while retrieving accounts." + e.getMessage());
        }
    }
    public static MessageInfo<Account> saveAccountChanges(Account acc) {
        String sql = "UPDATE accounts SET name = ?, password = ?, balance = ?, history = ? WHERE account = ?";
        try (Connection conn = getConnection(); 
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
            pstmt.setString(1, acc.name);
            pstmt.setString(2, acc.password);
            pstmt.setDouble(3, acc.get_balance());
            Object[] historyArray = acc.history.toArray();
            pstmt.setArray(4, conn.createArrayOf("TEXT", historyArray));
            pstmt.setString(5, acc.account); 
        
            int rowsAffected = pstmt.executeUpdate();
        
            if (rowsAffected > 0) {
                return new MessageInfo<>(true, "Database synchronized successfully.");
            } else {
                return new MessageInfo<>(false, "Update failed.");
            }
        } catch (SQLException e) {
            return new MessageInfo<>(false, "Database error: " + e.getMessage());
        }
    }
}
