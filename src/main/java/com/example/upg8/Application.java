package com.example.upg8;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import java.util.Optional;

/*
NOTE: This application has only been tested with gmail.
*/

public class Application extends javafx.application.Application {

    public GridPane root = setGridPane();
    private AccountInfo accountInfo;

    @Override
    public void start(Stage stage) {
        sendEmail();
        login();
        Scene scene = new Scene(root, 800, 800);
        new Alert(Alert.AlertType.INFORMATION, "Welcome to the Mail program").showAndWait();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("Hello!");
        stage.show();
    }

    private GridPane setGridPane() {
        GridPane root = new GridPane();
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(25, 25, 25, 25));
        return root;
    }

    private void sendEmail() {
        root.add(new Label("To:"), 0, 0);
        TextField to = new TextField() {{setPromptText("To");}};
        root.add(to, 1, 0);
        root.add(new Label("Subject:"), 0, 1);
        TextField subject = new TextField() {{setPromptText("Subject");}};
        root.add(subject, 1, 1);
        root.add(new Label("Message:"), 0, 2);
        TextArea message = new TextArea() {{setPromptText("Message");}};
        root.add(message, 1, 2);
        Button sendButton = new Button("Send");
        root.add(sendButton, 0, 3);


        sendButton.setDisable(true);

        to.textProperty().addListener((observable, oldValue, newValue) -> {
            sendButton.setDisable(newValue.trim().isEmpty());
            sendButton.setDisable(!newValue.trim().contains("@"));
        });

        sendButton.setOnMouseClicked(event -> {
            try {
              Mail m = new Mail(accountInfo, message.getText(), subject.getText(), to.getText());
              m.send();
              new Alert(Alert.AlertType.INFORMATION, "Email sent").showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error! Email could not be sent.").showAndWait();
            }
        });
    }


    private void login() {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Login");
        dialog.setHeaderText("Email account to send from");

        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
        GridPane grid = setGridPane();

        TextField email = new TextField() {{setPromptText("Email");}};
        PasswordField password = new PasswordField() {{setPromptText("Password");}};

        grid.add(new Label("Email:"), 0, 0);
        grid.add(email, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(password, 1, 1);

        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        email.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
            loginButton.setDisable(!newValue.contains("@"));
            loginButton.setDisable(!newValue.trim().contains("Yahoo") );
        });

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> dialogButton == loginButtonType ? new Pair<>(email.getText(), password.getText()) : null);

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(res -> accountInfo = new AccountInfo(res.getKey(), res.getValue()));

        if (result.isEmpty()) {
            Platform.exit();
            System.exit(0);
        }
    }
}