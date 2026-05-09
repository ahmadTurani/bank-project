package com.Bank_files;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class BankSystem extends Application {
    
    class AlertBox {
        public static void display(String message) {
        Stage window = new Stage();
        window.setTitle("Message");

        TextArea textArea = new TextArea(message);
        textArea.setWrapText(true);       // enables multi-line wrapping
        textArea.setEditable(false);      // read-only

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> window.close());
        VBox layout = new VBox(10);
        layout.getChildren().addAll(textArea, closeButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout, 300, 150);
        window.setScene(scene);
        window.showAndWait();
    }
    }
    private Stage primStage; 
    @Override
    public void start(Stage primStage){
        this.primStage = primStage;
        mainMenue();
    }
    public Button setButtonDesign(String text){
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #d8d8d8; -fx-text-fill: DARKGOLDENROD; -fx-border-style: solid ; -fx-font-weight: bold; -fx-border-color: black;");
        button.setPrefWidth(100);
        button.setPrefHeight(35);
        button.setOnMouseEntered(e ->{
        button.setStyle("-fx-background-color: #b4b4b4; -fx-text-fill: DARKGOLDENROD; -fx-border-style: solid ; -fx-font-weight: bold; -fx-border-color: black;");
        });
        button.setOnMouseExited(e ->{
            button.setStyle("-fx-background-color: #d8d8d8; -fx-text-fill: DARKGOLDENROD; -fx-border-style: solid ; -fx-font-weight: bold; -fx-border-color: black;");
        });
        return button;
    }
    public Button retunButton(String text){
        Button returnbtn = setButtonDesign(text);
        returnbtn.setOnAction(e ->{
            mainMenue();
        });
        return returnbtn; 
    }
    public void mainMenue(){
        StackPane border= new StackPane();
        border.setStyle("-fx-background-color: black; -fx-padding: 15;");
        // main title
            Label bankTitle1 = new Label("Bank of Aswd");
                bankTitle1.setStyle("-fx-background-color: #b4b4b4; -fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: DARKGOLDENROD");
                bankTitle1.setLayoutX(400);
                bankTitle1.setLayoutY(50);
            Label bankTitle2 = new Label("Safe option");
                bankTitle2.setStyle("-fx-background-color: #b4b4b4; -fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: DARKGOLDENROD");
                bankTitle2.setLayoutX(415);
                bankTitle2.setLayoutY(100);

        Image iconUImage = new Image("file:C:/Users/Asus/Desktop/java projects/demo/resources/AswdBank_icon.png");
        ImageView iconUIView = new ImageView(iconUImage);
        iconUIView.setFitWidth(500);
        iconUIView.setFitHeight(250);
        iconUIView.setPreserveRatio(true);
        iconUIView.setLayoutX(350);
        iconUIView.setLayoutY(150);

        Pane root = new Pane();
        root.setStyle("-fx-background-color: #7f7f7f;");
        border.getChildren().add(root);

        Scene scene = new Scene(border, 800, 500);
        
        GridPane sideBar = new GridPane();
        sideBar.setPrefSize(160, 360);    
        sideBar.setLayoutX(40);
        sideBar.setLayoutY(40);
        sideBar.setVgap(40);
        sideBar.setStyle("-fx-background-color: #b4b4b4; -fx-padding: 33;");
        //login button
        Button loginBtn = setButtonDesign("login");
        loginBtn.setOnAction(e ->{
            loginMenue();
        });
        //create button
        Button creatbtn = setButtonDesign("create");
        creatbtn.setOnAction(e ->{
            creatMenue();
        });
        //admin button
        Button adminbtn = setButtonDesign("admin access");
        adminbtn.setOnAction(e ->{
            Adminlogin();
        });
        //madeby button
        Button madebybtn = setButtonDesign("made by");
        madebybtn.setOnAction(e ->{
        });
        
        sideBar.add(loginBtn, 1, 0);
        sideBar.add(creatbtn, 1, 1);
        sideBar.add(adminbtn, 1, 2);
        sideBar.add(madebybtn, 1, 3);

        root.getChildren().addAll(sideBar, iconUIView, bankTitle1, bankTitle2);
        this.primStage.setTitle("aswd");
        this.primStage.setScene(scene);
        this.primStage.show();
    }
    public void loginMenue(){
        StackPane border= new StackPane();
        border.setStyle("-fx-background-color: black; -fx-padding: 15;");
        GridPane root = new GridPane();
        root.setStyle("-fx-background-color: #7f7f7f;");
        border.getChildren().add(root);
        Label empty = new Label();

        Label accounLbl = new Label();
        Label errorlbl = new Label();

        TextField accountField = new TextField();
        accountField.setPromptText("Enter account...");

        TextField passwordField = new TextField();
        passwordField.setPromptText("Enter password...");
        
        Button loginBtn = setButtonDesign("login");
        loginBtn.setOnAction(e ->{
            MessageInfo<Account> login_account = Bank.loginAccount(accountField.getText(), passwordField.getText());
                if (login_account.success) {
                    accountMenue(login_account.data);
                } else {
                    errorlbl.setText(login_account.messageString);
                }
        });
        Button backBtn = retunButton("return");

        root.setVgap(20);
        root.setAlignment(Pos.TOP_CENTER);
        root.add(empty, 1, 2);
        root.add(accountField, 1, 1);
        root.add(accounLbl, 1, 3);
        root.add(passwordField, 1, 4);
        root.add(errorlbl, 1, 5);
        root.add(loginBtn, 1, 6);
        root.add(backBtn, 1, 7);
        Scene scene = new Scene(border, 200, 450);



        this.primStage.setScene(scene);        
    }   
    public void Adminlogin(){
        StackPane border= new StackPane();
        border.setStyle("-fx-background-color: black; -fx-padding: 15;");
        GridPane root = new GridPane();
        root.setStyle("-fx-background-color: #7f7f7f;");
        border.getChildren().add(root);
        Label empty = new Label("");

        Label accounLbl = new Label();
        Label passwlbl = new Label();

        TextField accountField = new TextField();
        accountField.setPromptText("Enter account...");

        TextField passwordField = new TextField();
        passwordField.setPromptText("Enter password...");
        
        Button loginBtn = setButtonDesign("login");
        loginBtn.setOnAction(e ->{
            if (accountField.getText().equals("") || passwordField.getText().equals("")) {
                AlertBox.display("Please fill in all fields.");
            } else if (accountField.getText().equals("aswd@admin.com") && passwordField.getText().equals("AswdAdmin1234!@#$")) {
                AlertBox.display("Admin login successful!");
                adminMenue();
            } else {
                AlertBox.display("Invalid admin credentials!");
            }
        });
        Button backBtn = retunButton("return");

        root.setVgap(20);
        root.setAlignment(Pos.TOP_CENTER);
        root.add(empty, 1, 0);
        root.add(accountField, 1, 1);
        root.add(accounLbl, 1, 3);
        root.add(passwordField, 1, 4);
        root.add(passwlbl, 1, 5);
        root.add(loginBtn, 1, 6);
        root.add(backBtn, 1, 7);

        Scene scene = new Scene(border, 200, 450);



        this.primStage.setScene(scene);        
    }   
    public void creatMenue(){
        StackPane border= new StackPane();
        border.setStyle("-fx-background-color: black; -fx-padding: 15;");
        GridPane root = new GridPane();
        root.setStyle("-fx-background-color: #7f7f7f;");
        border.getChildren().add(root);
        Label empty = new Label();
        Label empty1 = new Label();

        Label accounLbl = new Label();
        Label errorlbl = new Label();

        TextField nameField = new TextField();
        nameField.setPromptText("Enter name...");

        TextField accountField = new TextField();
        accountField.setPromptText("Enter account...");

        TextField passwordField = new TextField();
        passwordField.setPromptText("Enter password...");
        
        Button createBtn = setButtonDesign("login");
        createBtn.setOnAction(e ->{
            MessageInfo<Account> new_account = Bank.createAccount(accountField.getText(), nameField.getText(), passwordField.getText());
            if (new_account.success) {
                accountMenue(new_account.data);
                Bank.login(new_account.data);
                }
                else{
                errorlbl.setText(new_account.messageString);
                }
        });
        Button backBtn = retunButton("return");

        root.setVgap(20);
        root.setAlignment(Pos.TOP_CENTER);
        root.add(empty, 1, 0);
        root.add(nameField, 1, 1);
        root.add(empty1, 1, 2);
        root.add(accountField, 1, 3);
        root.add(accounLbl, 1, 4);
        root.add(passwordField, 1, 5);
        root.add(errorlbl, 1, 6);
        root.add(createBtn, 1, 7);
        root.add(backBtn, 1, 9);

        Scene scene = new Scene(border, 200, 450);



        this.primStage.setScene(scene);        
    }   
    public void accountMenue(Account account){
        StackPane border= new StackPane();
        border.setStyle("-fx-background-color: black; -fx-padding: 15;");


        Pane root = new Pane();
        root.setStyle("-fx-background-color: #7f7f7f;");
        border.getChildren().add(root);

        Scene scene = new Scene(border, 800, 500);
        Label balancelbl = new Label("Balance: " + Bank.get_balance(account));
        balancelbl.setLayoutX(125);
        balancelbl.setLayoutY(100);
        balancelbl.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: DARKGOLDENROD");
        
        TextField amounField = new TextField();
        amounField.setPromptText("Enter amount...");
        amounField.setLayoutX(100);
        amounField.setLayoutY(15);

        Button withdrawBtn = setButtonDesign("withdraw");
        withdrawBtn.setLayoutX(200);
        withdrawBtn.setLayoutY(60);
        withdrawBtn.setOnAction(e ->{
            String amountstring = amounField.getText();
            double amount = Utilities.turn_double(amountstring);
            MessageInfo<Account> message = Bank.withdraw(amount, account);
            balancelbl.setText("Balance: " + Bank.get_balance(account));
            AlertBox.display(message.messageString);
        });

        Button depositBtn = setButtonDesign("deposit");
        depositBtn.setLayoutX(50);
        depositBtn.setLayoutY(60);
        depositBtn.setOnAction(e ->{
            String amountstring = amounField.getText();
            double amount = Utilities.turn_double(amountstring);
            MessageInfo<Account> message = Bank.deposit(amount, account);
            balancelbl.setText("Balance: " + Bank.get_balance(account));
            AlertBox.display(message.messageString);
        });
        Button logoutBtn = retunButton("logout");
        logoutBtn.setLayoutX(30);
        logoutBtn.setLayoutY(350);
        Button historyBtn = setButtonDesign("history");
        historyBtn.setLayoutX(30);
        historyBtn.setLayoutY(250);
        historyBtn.setOnAction(e ->{
            String historyString = "";
            for (String record : Bank.showHistory(account)) {
                historyString += record + "\n";
            }
            AlertBox.display(historyString);
        });

        Button transferbtn = setButtonDesign("transfer");
        transferbtn.setLayoutX(30);
        transferbtn.setLayoutY(150);
        transferbtn.setOnAction(e ->{
            transferMenue(account);
        });

        root.getChildren().addAll(depositBtn, withdrawBtn, logoutBtn, amounField, balancelbl, transferbtn, historyBtn);
        this.primStage.setTitle("aswd");
        this.primStage.setScene(scene);
        this.primStage.show();

    }
    public void transferMenue(Account account){
        StackPane border= new StackPane();
        border.setStyle("-fx-background-color: black; -fx-padding: 15;");
        Pane root = new Pane();
        root.setStyle("-fx-background-color: #7f7f7f;");
        border.getChildren().add(root);
        Scene scene = new Scene(border, 300, 500);
        TextField targetAccountField = new TextField();
        targetAccountField.setPromptText("Enter target account...");
        targetAccountField.setLayoutX(30);
        targetAccountField.setLayoutY(15);
        TextField amounField = new TextField();
        amounField.setPromptText("Enter amount...");
        amounField.setLayoutX(30);
        amounField.setLayoutY(60);
        Label messageLbl = new Label();

        Button transferBtn = setButtonDesign("transfer");
        transferBtn.setLayoutX(30);
        transferBtn.setLayoutY(100);
            transferBtn.setOnAction(e ->{
                String targetAccount = targetAccountField.getText();
                String amountstring = amounField.getText();
                double amount = Utilities.turn_double(amountstring);
                MessageInfo<Account> message = Bank.transfer(targetAccount, account, amount);
                AlertBox.display(message.messageString);
            });
        Button backBtn = setButtonDesign("return");
        backBtn.setLayoutX(30);
        backBtn.setLayoutY(150);
        backBtn.setOnAction(e ->{
            accountMenue(account);
        });
        root.getChildren().addAll(targetAccountField, amounField, transferBtn, backBtn);
        this.primStage.setTitle("aswd");
        this.primStage.setScene(scene);
        this.primStage.show();
    }
    public void adminMenue(){
        StackPane border= new StackPane();
        border.setStyle("-fx-background-color: black; -fx-padding: 15;");
        Pane root = new Pane();
        root.setStyle("-fx-background-color: #7f7f7f;");
        border.getChildren().add(root);
        Scene scene = new Scene(border, 300, 500);
        

        ComboBox<String> accountComboBox = new ComboBox<>();
        accountComboBox.getItems().addAll(Bank.getAllAccountsNames().data);
        accountComboBox.setLayoutX(30);
        accountComboBox.setLayoutY(60);
        
        Button enteraccBtn = setButtonDesign("enter account");
        enteraccBtn.setLayoutX(30);
        enteraccBtn.setLayoutY(100);
        enteraccBtn.setOnAction(e ->{
            Account selectedAccount = Bank.loginAccount(accountComboBox.getValue()).data;
            if (selectedAccount != null) {
                accountMenue(selectedAccount);
            }
        });
        Button backBtn = retunButton("return");
        backBtn.setLayoutX(30);
        backBtn.setLayoutY(150);
        root.getChildren().addAll(backBtn, accountComboBox, enteraccBtn);
        this.primStage.setTitle("aswd");
        this.primStage.setScene(scene);
        this.primStage.show();

    }
    public static void main(String[] args) {
        launch(args);
    }
}