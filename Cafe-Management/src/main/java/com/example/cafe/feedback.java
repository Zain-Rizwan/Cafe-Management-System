package com.example.cafe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class feedback {
    String review;
    private Connection connect;

    feedback(){
        review="";
    }
    feedback(String s){
        review=s;
    }
    public void showfeedback(){
        System.out.println(review);
    }
    public void saveFeedback(customer c,String s){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  // Use the updated driver class
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/cafedb", "root", "cake1470*");
            System.out.println("DB connected");  // Print a message to verify the connection
            String insertQuery = "INSERT INTO feedback (name, review, payment) VALUES (?, ?, ?)";
            try (PreparedStatement insertStatement = connect.prepareStatement(insertQuery)) {
                insertStatement.setString(1, c.username);
                insertStatement.setString(2, s);
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

    }


}
