package org.example.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;

public class ControlPaneController {

    @FXML
    private Button nextButton;

    @FXML
    private ToggleButton playButton;

    @FXML
    private Button previousButton;

    @FXML
    private Slider progressSlider;

    @FXML
    private Slider volumeSlider;
    @FXML
    private TextField messageTextField;

    public Button getNextButton() {
        return nextButton;
    }

    public ToggleButton getPlayButton() {
        return playButton;
    }

    public Button getPreviousButton() {
        return previousButton;
    }

    public Slider getProgressSlider() {
        return progressSlider;
    }

    public Slider getVolumeSlider() {
        return volumeSlider;
    }

    public TextField getMessageTextField() {
        return messageTextField;
    }
}
