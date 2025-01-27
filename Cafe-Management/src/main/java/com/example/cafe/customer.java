package com.example.cafe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class customer extends user{

    protected int amount;

    customer(){
        username="";
        password="";
    }


    customer(String u,String p){
        username=u;
        password=p;
    }

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    @Override
    void showinfo(){
        System.out.println(username);
        System.out.println(password);
        System.out.println(amount);
    }



}


