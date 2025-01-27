package com.example.cafe;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;


public class loggedinController {

    @FXML
    protected TextField l_username_t;
    @FXML
    protected TextField l_password_t;

    @FXML
    private void login_Button_Clicked() throws SQLException, IOException {
        System.out.println("Login done");
        if(l_username_t.getText().isEmpty()){
            System.out.println("Enter Username");
        }
        else if(l_password_t.getText().isEmpty()){
            System.out.println("Enter Password");
        }
        else {
            Connection connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/cafedb","root","cake1470*");
//                Statement statement= connection.createStatement();
//                ResultSet resultSet= statement.executeQuery("select * from users");
//                resultSet.next();
//                //while(resultSet.next()){
//                    System.out.println(resultSet.getString("user_name"));
//                //}

            boolean isValid = false;
            try {
                String query = "SELECT * FROM users WHERE user_name = ? AND `user_password` = ?";

                // Prepare the SQL statement
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, l_username_t.getText());
                preparedStatement.setString(2, l_password_t.getText());

                // Execute the query
                ResultSet resultSet = preparedStatement.executeQuery();

                // Check if a row was returned
                if (resultSet.next()) {
                    isValid = true; // Username and password are valid
                }

                // Close resources
                resultSet.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if(isValid) {
                //Connection connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/cafedb","root","cake1470*");
                String updateQuery = "UPDATE users SET loyalty = loyalty + 1 WHERE user_name = ?";

                //try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
                     PreparedStatement preparedStatement = connection.prepareStatement(updateQuery) ;

                    preparedStatement.setString(1, l_username_t.getText());

                    int rowsAffected = preparedStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        System.out.println("Loyalty incremented for user name " + l_username_t.getText());
                    } else {
                        System.out.println("User not found or loyalty not updated.");
                    }

                if(l_username_t.getText().equals("Zain")){
                    load_staff_page();
                }
                else {
                    load_login_page();
                }
            }
            else{
                System.out.println("Wrong Credentials\n");
            }



        }

    }
    protected void load_staff_page() throws IOException {
        //c.print();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/cafe/staff_page.fxml"));
        Parent root = fxmlLoader.load();

        // Assuming your signup page controller has a method to set up initial data.


        Stage stage = new Stage();
        stage.setTitle("Staff Page");
        stage.setScene(new Scene(root));
        stage.show();
    }


    protected void load_login_page(){
        try {
            //System.out.println(s_password_t.getText());
            customer c =new customer(l_username_t.getText(),l_password_t.getText());
            //c.print();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/cafe/customer_page.fxml"));
            Parent root = fxmlLoader.load();

            // Assuming your signup page controller has a method to set up initial data.
            customerController cus = fxmlLoader.getController();
            cus.initialize(c);  // Adjust this line accordingly.


            Stage stage = new Stage();
            stage.setTitle("Logged in Page");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception appropriately, e.g., show an error message.
        }
    }

    @FXML
    private void register_Button_Clicked() throws SQLException{
            try {
                //System.out.println(s_password_t.getText());
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/cafe/signin.fxml"));
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
