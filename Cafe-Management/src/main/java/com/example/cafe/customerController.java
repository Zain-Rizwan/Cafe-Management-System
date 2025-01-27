package com.example.cafe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;


public class customerController {

    customer c;
    Cart cart=new Cart();

    private String s1;
    private String s2;
    private String s3;
    private String s4;
    private boolean b1=true;
    private boolean b2=true;
    private boolean b3=true;
    private boolean b4=true;


    @FXML
    private ComboBox<Object> combo_c;

    @FXML
    private ComboBox<Object> combo_n;

    @FXML
    private ComboBox<Object> combo_p;

    //@FXML
    //private ComboBox<Object> combo_q;

    @FXML
    private Label c_text;
    @FXML
    private TextField m_quantity_t;

    private Connection connect;

    public Connection connectDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  // Use the updated driver class
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/cafedb", "root", "cake1470*");
            System.out.println("DB connected");  // Print a message to verify the connection
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connect;
    }

    //@FXML
    public void combo() {
        String selectCategory = "SELECT DISTINCT category FROM food";
        connect = connectDB();

        try {
            PreparedStatement prepare = connect.prepareStatement(selectCategory);
            ResultSet result = prepare.executeQuery();
            ObservableList<Object> listCategory = FXCollections.observableArrayList();
            while (result.next()) {
                String item = result.getString("category");
                listCategory.add(item);
            }
            combo_c.setItems(listCategory);

            // Set an event handler for the combo box selection change event
            combo_c.setOnAction(event -> {
                String selectedCategory = (String) combo_c.getSelectionModel().getSelectedItem();
                c_text.setText(selectedCategory);
                s1=c_text.getText();
                b1=false;
            });

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void combo2() {
        String selectCategory = "SELECT food_name FROM food WHERE category=?";
        connect = connectDB();

        try {
            PreparedStatement prepare = connect.prepareStatement(selectCategory);
            prepare.setString(1, s1);
            ResultSet result = prepare.executeQuery();
            ObservableList<Object> listCategory = FXCollections.observableArrayList();
            while (result.next()) {
                String item = result.getString("food_name");
                listCategory.add(item);
            }
            combo_n.setItems(listCategory);

            // Set an event handler for the combo box selection change event
            combo_n.setOnAction(event -> {
                String selectedCategory = (String) combo_n.getSelectionModel().getSelectedItem();
                c_text.setText(selectedCategory);
                s2=c_text.getText();
                b2=false;
            });

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void combo3() {
        String selectCategory = "SELECT price FROM food WHERE category=? AND food_name=?";
        connect = connectDB();

        try {
            PreparedStatement prepare = connect.prepareStatement(selectCategory);
            prepare.setString(1, s1);
            prepare.setString(2, s2);
            ResultSet result = prepare.executeQuery();
            ObservableList<Object> listCategory = FXCollections.observableArrayList();
            while (result.next()) {
                String item = result.getString("price");
                listCategory.add(item);
            }
            combo_p.setItems(listCategory);

            // Set an event handler for the combo box selection change event
            combo_p.setOnAction(event -> {
                String selectedCategory = (String) combo_p.getSelectionModel().getSelectedItem();
                c_text.setText(selectedCategory);
                s3=c_text.getText();
                b3=false;
            });

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
//    public void quantity_show() throws SQLException {
//        String selectCategory = "SELECT quantity FROM food WHERE category=? AND food_name=? AND price=?";
//        PreparedStatement prepare = connect.prepareStatement(selectCategory);
//        prepare.setString(1, s1);
//        prepare.setString(2, s2);
//        prepare.setString(3, s3);
//        ResultSet result = prepare.executeQuery();
//        //ObservableList<Object> listCategory = FXCollections.observableArrayList();
//        while (result.next()) {
//            String item = result.getString("quantity");
//            //listCategory.add(item);
//        }
//        //combo_q.setItems(listCategory);
//
//        // Set an event handler for the combo box selection change event
//        combo_q.setOnAction(event -> {
//            String selectedCategory = (String) combo_q.getSelectionModel().getSelectedItem();
//            c_text.setText(selectedCategory);
//            s4=c_text.getText();
//        });
//
//    }
    public void add_to_cart(){
        if(m_quantity_t.getText().isEmpty()){
            c_text.setText("Enter Quantity");
            b4=true;
        }
        else if(b1 || b2 || b3){
            c_text.setText("Please give order first");
        }
        else{
            if(update_data(s2,Integer.parseInt(m_quantity_t.getText()))){
                int cash=Integer.parseInt(m_quantity_t.getText());
                int quan=Integer.parseInt(s3);

                cart.addOrder(s2,Integer.parseInt(m_quantity_t.getText()));

                c.amount=c.amount+(cash*quan);
                c_text.setText("Order Taken amount="+c.amount);
                combo_c.getItems().clear();
                combo_n.getItems().clear();
                combo_p.getItems().clear();
                m_quantity_t.setText("");
                c_text.setText(String.valueOf(c.amount));
            }
           else{
                c_text.setText("This quantity not in stock");
            }
        }
    }
    private boolean update_data(String name,int quan){
        boolean up=true;

        String selectQuery = "SELECT quantity FROM food WHERE food_name = ?";
        connect = connectDB();
        try (PreparedStatement selectStatement = connect.prepareStatement(selectQuery)) {
            selectStatement.setString(1, name);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                int existingQuantity = resultSet.getInt("quantity");
                System.out.println(existingQuantity);
                if (Integer.parseInt(m_quantity_t.getText()) <= existingQuantity) {
                    // Update the quantity
                    String updateQuery = "UPDATE food SET quantity = ? WHERE food_name = ?";
                    try (PreparedStatement updateStatement = connect.prepareStatement(updateQuery)) {
                        updateStatement.setInt(1, existingQuantity - Integer.parseInt(m_quantity_t.getText()));
                        updateStatement.setString(2, name);
                        int rowsAffected = updateStatement.executeUpdate();

                        if (rowsAffected > 0) {
                            System.out.println("Quantity updated successfully.");
                        } else {
                            System.out.println("Failed to update quantity.");
                            up=false;
                        }
                    }
                } else {
                    System.out.println("Insufficient quantity in stock.");
                    up=false;
                }
            } else {
                System.out.println("Food not found in the database.");
                up=false;
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return up;
    }




    @FXML
    private void checkout_Button_Clicked() throws SQLException{
        load_payment_page();
    }
    private void load_payment_page() {
        cart.printAllOrders();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/cafe/payment.fxml"));
            Parent root = fxmlLoader.load();

            paymentController cus = fxmlLoader.getController();
            cus.initialize(c,cart);  // Adjust this line accordingly.

            Stage stage = new Stage();
            stage.setTitle("Payment Page");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (
                IOException e) {
            e.printStackTrace();
            // Handle the exception appropriately, e.g., show an error message.
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void initialize(customer cus) {
        c=(customer) cus;
        c.showinfo();
        System.out.println("DONEEEEE");
    }


//    public void combo4() {
//        String selectCategory = "SELECT quantity FROM food WHERE category=? AND food_name=? AND price=?";
//        connect = connectDB();
//
//
//        try {
//            PreparedStatement prepare = connect.prepareStatement(selectCategory);
//            prepare.setString(1, s1);
//            prepare.setString(2, s2);
//            prepare.setString(3, s3);
//            ResultSet result = prepare.executeQuery();
//            ObservableList<Object> listCategory = FXCollections.observableArrayList();
//            while (result.next()) {
//                String item = result.getString("quantity");
//                listCategory.add(item);
//            }
//            combo_q.setItems(listCategory);
//
//            // Set an event handler for the combo box selection change event
//            combo_q.setOnAction(event -> {
//                String selectedCategory = (String) combo_q.getSelectionModel().getSelectedItem();
//                c_text.setText(selectedCategory);
//                s4=c_text.getText();
//            });
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }


//    @FXML
//    private void on_category_Clicked(){
//        System.out.println("Category Clicked");
//        c_text.setText("category");
//    }
//    @FXML
//    private void on_name_Clicked(){
//        System.out.println("Name Clicked");
//        c_text.setText("name");
//    }
//    @FXML
//    private void on_Price_Clicked(){
//        System.out.println("Price Clicked");
//        c_text.setText("Price");
//    }
//    @FXML
//    private void on_quantity_Clicked(){
//        System.out.println("quantity Clicked");
//        c_text.setText("Quantity");
//    }



}
