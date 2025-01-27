package com.example.cafe;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void sayByeButtonClick(){
        welcomeText.setText("Byee Byee");
        load_login_page();
    }

    @FXML
    protected void signupButtonClick(){
        welcomeText.setText(" Sign Uppp");
        load_signup_page();

    }

    private void load_login_page(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/cafe/loggedin.fxml"));
            Parent root = fxmlLoader.load();

            // Assuming your signup page controller has a method to set up initial data.
//            SignInController signupPageController = fxmlLoader.getController();
//            signupPageController.initialize();  // Adjust this line accordingly.

            Stage stage = new Stage();
            stage.setTitle("Logged in Page");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception appropriately, e.g., show an error message.
        }
    }

    private void load_signup_page(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/cafe/signin.fxml"));
            Parent root = fxmlLoader.load();

            // Assuming your signup page controller has a method to set up initial data.
//            SignInController signupPageController = fxmlLoader.getController();
//            signupPageController.initialize();  // Adjust this line accordingly.

            Stage stage = new Stage();
            stage.setTitle("Sign un Page");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception appropriately, e.g., show an error message.
        }
    }


}