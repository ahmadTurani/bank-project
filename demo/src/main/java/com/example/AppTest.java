package com.example;

import com.Bank_files.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;

public class AppTest extends Application {

    private Stage primaryStage;
    private Account loggedInAccount;

    // ─── Color Palette ───────────────────────────────────────────────────────────
    private static final String BG          = "#f0f0f0";
    private static final String PANEL_BG    = "#ffffff";
    private static final String ACCENT      = "#3a6ea5";
    private static final String ACCENT_DARK = "#2c5282";
    private static final String TEXT_DARK   = "#222222";
    private static final String TEXT_MUTED  = "#666666";
    private static final String SUCCESS_CLR = "#2d7a2d";
    private static final String ERROR_CLR   = "#b22222";
    private static final String BORDER_CLR  = "#cccccc";

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        primaryStage.setTitle("Bank of Aswd");
        primaryStage.setResizable(false);
        showMainMenu();
    }

    // ─── Helpers ─────────────────────────────────────────────────────────────────

    private Button makeBtn(String text, String bg, String fg) {
        Button b = new Button(text);
        b.setStyle(
                "-fx-background-color:" + bg + ";" +
                        "-fx-text-fill:" + fg + ";" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 13px;" +
                        "-fx-cursor: hand;" +
                        "-fx-border-radius: 4; -fx-background-radius: 4;" +
                        "-fx-padding: 7 18;"
        );
        b.setOnMouseEntered(e -> b.setStyle(
                "-fx-background-color:" + ACCENT_DARK + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 13px;" +
                        "-fx-cursor: hand;" +
                        "-fx-border-radius: 4; -fx-background-radius: 4;" +
                        "-fx-padding: 7 18;"
        ));
        b.setOnMouseExited(e -> b.setStyle(
                "-fx-background-color:" + bg + ";" +
                        "-fx-text-fill:" + fg + ";" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 13px;" +
                        "-fx-cursor: hand;" +
                        "-fx-border-radius: 4; -fx-background-radius: 4;" +
                        "-fx-padding: 7 18;"
        ));
        return b;
    }

    private Button primaryBtn(String text) {
        return makeBtn(text, ACCENT, "white");
    }

    private Button secondaryBtn(String text) {
        Button b = new Button(text);
        b.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-text-fill:" + ACCENT + ";" +
                        "-fx-font-size: 12px;" +
                        "-fx-cursor: hand;" +
                        "-fx-underline: true; -fx-padding: 3 0;"
        );
        return b;
    }

    private TextField styledField(String prompt) {
        TextField f = new TextField();
        f.setPromptText(prompt);
        f.setStyle(
                "-fx-background-color: white;" +
                        "-fx-border-color:" + BORDER_CLR + ";" +
                        "-fx-border-radius: 4; -fx-background-radius: 4;" +
                        "-fx-padding: 7 10;" +
                        "-fx-font-size: 13px;"
        );
        f.setPrefWidth(240);
        return f;
    }

    private PasswordField styledPass(String prompt) {
        PasswordField f = new PasswordField();
        f.setPromptText(prompt);
        f.setStyle(
                "-fx-background-color: white;" +
                        "-fx-border-color:" + BORDER_CLR + ";" +
                        "-fx-border-radius: 4; -fx-background-radius: 4;" +
                        "-fx-padding: 7 10;" +
                        "-fx-font-size: 13px;"
        );
        f.setPrefWidth(240);
        return f;
    }

    private Label msgLabel() {
        Label l = new Label();
        l.setWrapText(true);
        l.setMaxWidth(260);
        l.setStyle("-fx-font-size: 12px;");
        return l;
    }

    private void setMsg(Label lbl, String msg, boolean ok) {
        lbl.setText(msg);
        lbl.setStyle("-fx-font-size: 12px; -fx-text-fill: " + (ok ? SUCCESS_CLR : ERROR_CLR) + ";");
    }

    private VBox card(double width) {
        VBox box = new VBox(14);
        box.setMaxWidth(width);
        box.setStyle(
                "-fx-background-color:" + PANEL_BG + ";" +
                        "-fx-border-color:" + BORDER_CLR + ";" +
                        "-fx-border-radius: 8; -fx-background-radius: 8;" +
                        "-fx-effect: dropshadow(gaussian,rgba(0,0,0,0.10),8,0,0,2);" +
                        "-fx-padding: 30 35;"
        );
        return box;
    }

    private Label header(String text) {
        Label l = new Label(text);
        l.setFont(Font.font("SansSerif", FontWeight.BOLD, 20));
        l.setStyle("-fx-text-fill:" + TEXT_DARK + ";");
        return l;
    }

    private Label subHeader(String text) {
        Label l = new Label(text);
        l.setStyle("-fx-text-fill:" + TEXT_MUTED + "; -fx-font-size: 13px;");
        return l;
    }

    private Label fieldLabel(String text) {
        Label l = new Label(text);
        l.setStyle("-fx-font-size: 12px; -fx-text-fill:" + TEXT_MUTED + "; -fx-font-weight: bold;");
        return l;
    }

    private Separator sep() {
        Separator s = new Separator();
        s.setStyle("-fx-background-color:" + BORDER_CLR + ";");
        return s;
    }

    private StackPane wrapCenter(VBox card) {
        StackPane root = new StackPane(card);
        root.setStyle("-fx-background-color:" + BG + ";");
        StackPane.setAlignment(card, Pos.CENTER);
        return root;
    }

    // ─── 1. Main Menu ─────────────────────────────────────────────────────────────

    public void showMainMenu() {
        VBox c = card(320);

        Label title = new Label("🏦 Bank of Aswd");
        title.setFont(Font.font("SansSerif", FontWeight.BOLD, 24));
        title.setStyle("-fx-text-fill:" + ACCENT + ";");

        Label sub = subHeader("Simple. Secure. Reliable.");

        sep();

        Button loginBtn  = primaryBtn("Login");
        Button createBtn = primaryBtn("Create Account");
        loginBtn.setPrefWidth(240);
        createBtn.setPrefWidth(240);

        loginBtn.setOnAction(e  -> showLogin());
        createBtn.setOnAction(e -> showCreateAccount());

        Label footer = new Label("Bank of Aswd © 2026");
        footer.setStyle("-fx-font-size: 11px; -fx-text-fill:" + TEXT_MUTED + ";");

        c.setAlignment(Pos.CENTER);
        c.getChildren().addAll(title, sub, sep(), loginBtn, createBtn, sep(), footer);

        Scene s = new Scene(wrapCenter(c), 480, 400);
        primaryStage.setScene(s);
        primaryStage.show();
    }

    // ─── 2. Login ─────────────────────────────────────────────────────────────────

    public void showLogin() {
        VBox c = card(320);

        Label h = header("Login");
        Label sub = subHeader("Enter your account credentials");

        TextField accField  = styledField("e.g. john@bank.com");
        PasswordField passF = styledPass("Password");
        Label msg           = msgLabel();

        Button loginBtn = primaryBtn("Login");
        loginBtn.setPrefWidth(240);
        Button back = secondaryBtn("← Back to menu");

        loginBtn.setOnAction(e -> {
            String acc  = accField.getText().trim();
            String pass = passF.getText();
            if (acc.isEmpty() || pass.isEmpty()) {
                setMsg(msg, "Please fill all fields.", false);
                return;
            }
            MessageInfo<Account> res = Bank.loginAccount(acc, pass);
            if (res.success) {
                loggedInAccount = res.data;
                Bank.login(loggedInAccount);
                showDashboard();
            } else {
                setMsg(msg, res.messageString, false);
            }
        });

        back.setOnAction(e -> showMainMenu());

        c.setAlignment(Pos.CENTER_LEFT);
        c.getChildren().addAll(
                header("Login"), sub, sep(),
                fieldLabel("Account Email"), accField,
                fieldLabel("Password"), passF,
                msg, loginBtn, back
        );

        Scene s = new Scene(wrapCenter(c), 480, 460);
        primaryStage.setScene(s);
    }

    // ─── 3. Create Account ────────────────────────────────────────────────────────

    public void showCreateAccount() {
        VBox c = card(340);
        c.setAlignment(Pos.CENTER_LEFT);

        TextField accF  = styledField("e.g. yourname@bank.com");
        TextField nameF = styledField("Full name");
        PasswordField passF   = styledPass("Min 8 chars, upper+lower+digit+special");
        PasswordField confirmF = styledPass("Repeat password");
        Label msg = msgLabel();

        Button createBtn = primaryBtn("Create Account");
        createBtn.setPrefWidth(240);
        Button back = secondaryBtn("← Back to menu");

        createBtn.setOnAction(e -> {
            String acc     = accF.getText().trim();
            String name    = nameF.getText().trim();
            String pass    = passF.getText();
            String confirm = confirmF.getText();

            if (acc.isEmpty() || name.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
                setMsg(msg, "Please fill all fields.", false);
                return;
            }
            if (!pass.equals(confirm)) {
                setMsg(msg, "Passwords do not match.", false);
                return;
            }
            MessageInfo<Account> res = Bank.createAccount(acc, name, pass);
            if (res.success) {
                setMsg(msg, "Account created! You can now login.", true);
                accF.clear(); nameF.clear(); passF.clear(); confirmF.clear();
            } else {
                setMsg(msg, res.messageString, false);
            }
        });

        back.setOnAction(e -> showMainMenu());

        c.getChildren().addAll(
                header("Create Account"), subHeader("Fill in the details below"), sep(),
                fieldLabel("Account Email"), accF,
                fieldLabel("Full Name"), nameF,
                fieldLabel("Password"), passF,
                fieldLabel("Confirm Password"), confirmF,
                msg, createBtn, back
        );

        Scene s = new Scene(wrapCenter(c), 480, 540);
        primaryStage.setScene(s);
    }

    // ─── 4. Dashboard ─────────────────────────────────────────────────────────────

    public void showDashboard() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color:" + BG + ";");

        // ── Top bar
        HBox topBar = new HBox();
        topBar.setPadding(new Insets(12, 20, 12, 20));
        topBar.setStyle("-fx-background-color:" + ACCENT + ";");
        topBar.setAlignment(Pos.CENTER_LEFT);

        Label title = new Label("🏦 Bank of Aswd");
        title.setFont(Font.font("SansSerif", FontWeight.BOLD, 16));
        title.setStyle("-fx-text-fill: white;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label userLbl = new Label("👤 " + loggedInAccount.name);
        userLbl.setStyle("-fx-text-fill: white; -fx-font-size: 13px;");

        Button logoutBtn = new Button("Logout");
        logoutBtn.setStyle(
                "-fx-background-color: rgba(255,255,255,0.2);" +
                        "-fx-text-fill: white; -fx-cursor: hand; -fx-font-size: 12px;" +
                        "-fx-border-radius:4; -fx-background-radius:4; -fx-padding:4 12;"
        );
        logoutBtn.setOnAction(e -> {
            Bank.logout(loggedInAccount);
            loggedInAccount = null;
            showMainMenu();
        });

        topBar.getChildren().addAll(title, spacer, userLbl, new Label("   "), logoutBtn);
        root.setTop(topBar);

        // ── Center tabs
        TabPane tabs = new TabPane();
        tabs.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabs.setStyle("-fx-background-color:" + BG + ";");

        tabs.getTabs().addAll(
                makeOverviewTab(),
                makeDepositTab(),
                makeWithdrawTab(),
                makeTransferTab(),
                makeHistoryTab()
        );

        root.setCenter(tabs);

        Scene s = new Scene(root, 620, 520);
        primaryStage.setScene(s);
    }

    private Tab makeOverviewTab() {
        Tab tab = new Tab("Overview");
        VBox c = card(360);
        c.setAlignment(Pos.CENTER_LEFT);
        c.setPadding(new Insets(30));

        Label greeting = new Label("Hello, " + loggedInAccount.name + " 👋");
        greeting.setFont(Font.font("SansSerif", FontWeight.BOLD, 18));
        greeting.setStyle("-fx-text-fill:" + TEXT_DARK + ";");

        Label accountLbl = new Label("Account: " + loggedInAccount.account);
        accountLbl.setStyle("-fx-text-fill:" + TEXT_MUTED + "; -fx-font-size: 13px;");

        Label idLbl = new Label("ID: #" + loggedInAccount.ID);
        idLbl.setStyle("-fx-text-fill:" + TEXT_MUTED + "; -fx-font-size: 13px;");

        // Balance box
        VBox balBox = new VBox(4);
        balBox.setStyle(
                "-fx-background-color:" + ACCENT + ";" +
                        "-fx-background-radius:8; -fx-padding: 18 24;"
        );
        Label balTitle = new Label("Current Balance");
        balTitle.setStyle("-fx-text-fill: rgba(255,255,255,0.8); -fx-font-size:12px;");
        Label balAmt = new Label("$" + String.format("%.2f", Bank.get_balance(loggedInAccount)));
        balAmt.setFont(Font.font("SansSerif", FontWeight.BOLD, 28));
        balAmt.setStyle("-fx-text-fill: white;");
        balBox.getChildren().addAll(balTitle, balAmt);

        Button refreshBtn = secondaryBtn("↻ Refresh balance");
        refreshBtn.setOnAction(e -> {
            double bal = Bank.checkBalance(loggedInAccount);
            balAmt.setText("$" + String.format("%.2f", bal));
        });

        StackPane wrap = new StackPane(c);
        wrap.setStyle("-fx-background-color:" + BG + ";");
        wrap.setPadding(new Insets(30));
        StackPane.setAlignment(c, Pos.CENTER);

        c.getChildren().addAll(greeting, accountLbl, idLbl, sep(), balBox, refreshBtn);
        tab.setContent(wrap);
        return tab;
    }

    private Tab makeDepositTab() {
        Tab tab = new Tab("Deposit");
        VBox c = card(340);
        c.setAlignment(Pos.CENTER_LEFT);

        TextField amtF = styledField("Amount (e.g. 100.00)");
        Label msg = msgLabel();
        Button btn = primaryBtn("Deposit");
        btn.setPrefWidth(240);

        btn.setOnAction(e -> {
            double amt = Utilities.turn_double(amtF.getText().trim());
            if (amt < 0) { setMsg(msg, "Invalid amount.", false); return; }
            MessageInfo<Account> res = Bank.deposit(amt, loggedInAccount);
            setMsg(msg, res.messageString, res.success);
            if (res.success) amtF.clear();
        });

        StackPane wrap = new StackPane(c);
        wrap.setStyle("-fx-background-color:" + BG + ";");
        wrap.setPadding(new Insets(30));
        StackPane.setAlignment(c, Pos.CENTER);

        c.getChildren().addAll(
                header("Deposit"), subHeader("Add funds to your account"), sep(),
                fieldLabel("Amount ($)"), amtF, msg, btn
        );
        tab.setContent(wrap);
        return tab;
    }

    private Tab makeWithdrawTab() {
        Tab tab = new Tab("Withdraw");
        VBox c = card(340);
        c.setAlignment(Pos.CENTER_LEFT);

        TextField amtF = styledField("Amount (e.g. 50.00)");
        Label msg = msgLabel();
        Button btn = primaryBtn("Withdraw");
        btn.setPrefWidth(240);

        btn.setOnAction(e -> {
            double amt = Utilities.turn_double(amtF.getText().trim());
            if (amt < 0) { setMsg(msg, "Invalid amount.", false); return; }
            MessageInfo<Account> res = Bank.withdraw(amt, loggedInAccount);
            setMsg(msg, res.messageString, res.success);
            if (res.success) amtF.clear();
        });

        StackPane wrap = new StackPane(c);
        wrap.setStyle("-fx-background-color:" + BG + ";");
        wrap.setPadding(new Insets(30));
        StackPane.setAlignment(c, Pos.CENTER);

        c.getChildren().addAll(
                header("Withdraw"), subHeader("Take money from your account"), sep(),
                fieldLabel("Amount ($)"), amtF, msg, btn
        );
        tab.setContent(wrap);
        return tab;
    }

    private Tab makeTransferTab() {
        Tab tab = new Tab("Transfer");
        VBox c = card(340);
        c.setAlignment(Pos.CENTER_LEFT);

        TextField targetF = styledField("Recipient account (e.g. friend@bank.com)");
        TextField amtF    = styledField("Amount (e.g. 25.00)");
        Label msg = msgLabel();
        Button btn = primaryBtn("Transfer");
        btn.setPrefWidth(240);

        btn.setOnAction(e -> {
            String target = targetF.getText().trim();
            double amt    = Utilities.turn_double(amtF.getText().trim());
            if (target.isEmpty()) { setMsg(msg, "Enter target account.", false); return; }
            if (amt < 0)          { setMsg(msg, "Invalid amount.", false); return; }
            MessageInfo<Account> res = Bank.transfer(target, loggedInAccount, amt);
            setMsg(msg, res.messageString, res.success);
            if (res.success) { targetF.clear(); amtF.clear(); }
        });

        StackPane wrap = new StackPane(c);
        wrap.setStyle("-fx-background-color:" + BG + ";");
        wrap.setPadding(new Insets(30));
        StackPane.setAlignment(c, Pos.CENTER);

        c.getChildren().addAll(
                header("Transfer"), subHeader("Send money to another account"), sep(),
                fieldLabel("Recipient Account"), targetF,
                fieldLabel("Amount ($)"), amtF,
                msg, btn
        );
        tab.setContent(wrap);
        return tab;
    }

    private Tab makeHistoryTab() {
        Tab tab = new Tab("History");

        VBox outer = new VBox(14);
        outer.setPadding(new Insets(25));
        outer.setStyle("-fx-background-color:" + BG + ";");

        Label h   = header("Transaction History");
        Label sub = subHeader("Your recent account activity");

        ListView<String> list = new ListView<>();
        list.setPrefHeight(320);
        list.setStyle(
                "-fx-background-color: white;" +
                        "-fx-border-color:" + BORDER_CLR + ";" +
                        "-fx-border-radius:6; -fx-background-radius:6;" +
                        "-fx-font-size:12px;"
        );

        Button refreshBtn = primaryBtn("Load / Refresh History");
        refreshBtn.setOnAction(e -> {
            ArrayList<String> hist = Bank.showHistory(loggedInAccount);
            list.getItems().clear();
            if (hist == null || hist.isEmpty()) {
                list.getItems().add("No history yet.");
            } else {
                // show newest first
                for (int i = hist.size() - 1; i >= 0; i--) {
                    list.getItems().add(hist.get(i));
                }
            }
        });

        outer.getChildren().addAll(h, sub, refreshBtn, list);
        tab.setContent(outer);
        return tab;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
