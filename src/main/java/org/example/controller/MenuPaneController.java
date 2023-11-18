package org.example.controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuPaneController {

    @FXML
    private MenuItem aboutMenuItem;

    @FXML
    private MenuItem closeMenuItem;

    @FXML
    private MenuItem dirMenuItem;

    @FXML
    private MenuItem toTuiMenuItem;

    @FXML
    private MenuItem fileMenuItem;

    public void initialize(){
        configureMenu();

    }

    public MenuItem getAboutMenuItem() {
        return aboutMenuItem;
    }

    public MenuItem getCloseMenuItem() {
        return closeMenuItem;
    }

    public MenuItem getDirMenuItem() {
        return dirMenuItem;
    }

    public MenuItem getFileMenuItem() {
        return fileMenuItem;
    }

    public MenuItem getToTuiMenuItem() {
        return toTuiMenuItem;
    }

    public void configureMenu(){
        aboutMenuItem.setOnAction(actionEvent -> {
            try {
                Parent parent = FXMLLoader.load(getClass().getResource("/fxml/aboutPane.fxml"));
                Scene scene = new Scene(parent);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e){
                e.printStackTrace();
            }
        });
    }
}

