module com.Bank_files {
    requires javafx.controls;
    requires javafx.graphics;
    requires java.sql;

    exports com.example to javafx.graphics;
    exports com.Bank_files to javafx.graphics;

    opens com.example to javafx.graphics;
    opens com.Bank_files to javafx.graphics;

}