module com.example.upg8 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.mail;

    opens com.example.upg8 to javafx.fxml;
    exports com.example.upg8;
}