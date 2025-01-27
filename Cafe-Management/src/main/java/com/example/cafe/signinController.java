package com.example.cafe;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.example.cafe.*;

import java.io.IOException;
import java.sql.*;

public class signinController {

        @FXML
        protected TextField s_username_t;

        @FXML
        protected TextField s_password_t;

        @FXML
        private void register_Button_Clicked() throws SQLException {

                if (s_username_t.getText().isEmpty()) {
                    System.out.println("Enter Username");
                } else if (s_password_t.getText().isEmpty()) {
                    System.out.println("Enter Password");
                } else {
                    Connection connection = null;
                    try {
                        // Establish a connection to the database
                        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cafedb", "root", "cake1470*");

                        // Count the number of rows to generate a unique user_id
                        String countQuery = "SELECT COUNT(*) FROM users";
                        PreparedStatement countStatement = connection.prepareStatement(countQuery);
                        ResultSet countResult = countStatement.executeQuery();
                        countResult.next();
                        int rowCount = countResult.getInt(1);

                        // Generate a unique user_id by incrementing the row count
                        int userId = rowCount + 1;

                        // Insert the new user into the database
                        String insertQuery = "INSERT INTO users (user_id, user_name, user_password,loyalty) VALUES (?, ?, ?,?)";
                        PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                        insertStatement.setInt(1, userId);
                        insertStatement.setString(2, s_username_t.getText());
                        insertStatement.setString(3, s_password_t.getText());
                        insertStatement.setString(4, "0");

                        // Execute the insert query
                        int rowsAffected = insertStatement.executeUpdate();

                        // Check if the user was successfully inserted
                        if (rowsAffected > 0) {
                            System.out.println("User registered successfully!");
                            load_registerpage();

                        } else {
                            System.out.println("Failed to register user.");
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (connection != null && !connection.isClosed()) {
                                connection.close();
                                System.out.println("Connection closed.");
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }


        }

        protected void load_registerpage(){
            try {
                //System.out.println(s_password_t.getText());
                customer c =new customer(s_username_t.getText(),s_password_t.getText());
                c.showinfo();

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


}
