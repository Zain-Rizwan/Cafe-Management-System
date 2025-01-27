package com.example.cafe;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class paymentController {

    customer cus;
    Cart cart;
    @FXML
    private Label p_payment_t;
    public void initialize(customer c, Cart car) throws SQLException {
        cus = (customer) c;
        c.showinfo();
        cart = (Cart) car;
        cart.printAllOrders();
        Connection connection;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cafedb", "root", "cake1470*");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String selectQuery = "SELECT user_id, loyalty FROM users WHERE user_name = ?";
        String updateQuery = "UPDATE users SET loyalty = 0 WHERE user_id = ?";


        PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
        PreparedStatement updateStatement = connection.prepareStatement(updateQuery);

            // Check loyalty for the user
            selectStatement.setString(1, cus.username);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                int loyalty = resultSet.getInt("loyalty");

                if (loyalty >= 5) {
                    System.out.println("Loyalty is applicable for user " +  cus.username + ". Resetting loyalty to 0.");
                    // Reset loyalty to 0
                    updateStatement.setInt(1, userId);
                    updateStatement.executeUpdate();
                    cus.amount=(cus.amount*90)/100;
                } else {
                    System.out.println("Loyalty is not applicable for user " +  cus.username);
                }
            } else {
                System.out.println("User not found.");
            }




        p_payment_t.setText("Your total payment is: " + cus.amount);
//        System.out.println("DONEEEEE");
//        System.out.println("Payment here");
    }
    @FXML
    private void feedback_Button_Clicked(){
        load_feedback_page();
    }
    private void load_feedback_page(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/cafe/feedback.fxml"));
            Parent root = fxmlLoader.load();

            feedbackController f = fxmlLoader.getController();
            f.initialize(cus);  // Adjust this line accordingly.

            Stage stage = new Stage();
            stage.setTitle("Feedback Page");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (
                IOException e) {
            e.printStackTrace();
            // Handle the exception appropriately, e.g., show an error message.
        }
    }


}
