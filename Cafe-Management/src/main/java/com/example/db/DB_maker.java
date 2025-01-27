package com.example.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DB_maker {
    public void connect_db(){
        Connection connect;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  // Use the updated driver class
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/cafedb", "root", "cake1470*");
            System.out.println("DB connected");  // Print a message to verify the connection
        } catch (Exception e) {
            e.printStackTrace();
        }
        //return connect;
    }

}
