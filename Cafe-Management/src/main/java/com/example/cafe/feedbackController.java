package com.example.cafe;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class feedbackController {

    customer c;
    feedback f;

    @FXML
    private TextField f_feedback_t;
    @FXML
    private Label feedback_not;
    public void initialize(customer cus) {
        c=(customer) cus;
    }

    public void on_submit_Clicked(){
        if(f_feedback_t.getText().isEmpty()){
            feedback_not.setText("Please submit feedback first");
        }
        else{
//            f.review=feedback_not.getText();
//            f.saveFeedback(c,f.review);
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");  // Use the updated driver class
                Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/cafedb", "root", "cake1470*");
                System.out.println("DB connected");  // Print a message to verify the connection
                String insertQuery = "INSERT INTO feedback (name, review, payment) VALUES (?, ?, ?)";
                try (PreparedStatement insertStatement = connect.prepareStatement(insertQuery)) {
                    insertStatement.setString(1, c.username);
                    insertStatement.setString(2, f_feedback_t.getText());
                    insertStatement.setInt(3, c.amount);

                    int rowsAffected = insertStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        System.out.println("Feedback inserted successfully.");
                    } else {
                        System.out.println("Failed to insert feedback.");
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            load_exit_page();
        }
    }
    private void load_exit_page(){
        System.out.println("Exiting");
        try {
            //System.out.println(s_password_t.getText());
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/cafe/logout.fxml"));
            Parent root = fxmlLoader.load();

            // Assuming your signup page controller has a method to set up initial data.
//            SignInController signupPageController = fxmlLoader.getController();
//            signupPageController.initialize();  // Adjust this line accordingly.

            Stage stage = new Stage();
            stage.setTitle("Thank U Page");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception appropriately, e.g., show an error message.
        }
    }

}
