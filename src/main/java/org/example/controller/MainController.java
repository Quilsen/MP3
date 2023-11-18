package org.example.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.mp3.Mp3Parser;
import org.example.mp3.Mp3Song;
import org.example.player.Mp3Player;

import java.io.File;

public class MainController {
    @FXML
    private MenuPaneController menuPaneController;
    @FXML
    private ContentPaneController contentPaneController;
    @FXML
    private ControlPaneController controlPaneController;
    private Mp3Player mp3Player;

    public void initialize(){
        createPlayer();
        configureTableClick();
        configureButtons();
        configureMenu();
    }

    public void createPlayer(){
        ObservableList<Mp3Song> items = contentPaneController.getContentTable().getItems();
        mp3Player = new Mp3Player(items);
    }

    public void configureTableClick(){
        TableView<Mp3Song> contentTable = contentPaneController.getContentTable();
        contentTable.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if (mouseEvent.getClickCount() == 2){
                int selectedIndex = contentTable.getSelectionModel().getSelectedIndex();
                playSelectedSong(selectedIndex);
            }
        });
    }

    private void playSelectedSong(int selectedIndex) {
        mp3Player.loadSong(selectedIndex);
        configureProgressBar();
        configureVolume();
        controlPaneController.getPlayButton().setSelected(true);
    }

    private void configureVolume() {
        Slider volumeSlider = controlPaneController.getVolumeSlider();
        volumeSlider.valueProperty().unbind();
        volumeSlider.setMax(1.0);
        volumeSlider.valueProperty().bindBidirectional(mp3Player.getMediaplayer().volumeProperty());
    }

    private void configureProgressBar() {
        Slider progressSlider = controlPaneController.getProgressSlider();
        mp3Player.getMediaplayer().setOnReady(() -> progressSlider.setMax(mp3Player.getLoadedSongLength()));
        mp3Player.getMediaplayer().currentTimeProperty().addListener((observableValue, duration, t1) -> {
            progressSlider.setValue(t1.toSeconds());
        });
        progressSlider.valueProperty().addListener((observableValue, number, t1) -> {
            if (progressSlider.isValueChanging()){
                mp3Player.getMediaplayer().seek(Duration.seconds(t1.doubleValue()));
            }
        });
        
    }

    private void configureButtons(){
        TableView<Mp3Song> contentTable = contentPaneController.getContentTable();
        ToggleButton playButton = controlPaneController.getPlayButton();
        Button previousButton = controlPaneController.getPreviousButton();
        Button nextButton = controlPaneController.getNextButton();

        playButton.setOnAction(actionEvent -> {
            if (playButton.isSelected())
                mp3Player.play();
            else
                mp3Player.stop();
        });

        nextButton.setOnAction(actionEvent -> {
            contentTable.getSelectionModel().select(contentTable.getSelectionModel().getSelectedIndex() + 1);
            playSelectedSong(contentTable.getSelectionModel().getSelectedIndex());
        });

        previousButton.setOnAction(event -> {
            contentTable.getSelectionModel().select(contentTable.getSelectionModel().getSelectedIndex() - 1);
            playSelectedSong(contentTable.getSelectionModel().getSelectedIndex());
        });
    }
//    private void addTestMp3() {
//        ObservableList<Mp3Song> items = contentPaneController.getContentTable().getItems();
//        Mp3Song mp3SongFromPath = createMp3SongFromPath("muzyka.mp3");
//        items.add(mp3SongFromPath);
//        items.add(mp3SongFromPath);
//        items.add(mp3SongFromPath);
//    }
//
//    private Mp3Song createMp3SongFromPath(String filePath) {
//        File file = new File(filePath);
//        try {
//            MP3File mp3File = new MP3File(file);
//            String absolutePath = file.getAbsolutePath();
//            String title = mp3File.getID3v2Tag().getSongTitle();
//            String author = mp3File.getID3v2Tag().getLeadArtist();
//            String album = mp3File.getID3v2Tag().getAlbumTitle();
//            return new Mp3Song(title, author, album, absolutePath);
//        } catch (IOException | TagException e) {
//            e.printStackTrace();
//            return null; //ignore
//        }
//    }
//

    public void configureMenu(){
        MenuItem openFile = menuPaneController.getFileMenuItem();
        MenuItem openDir = menuPaneController.getDirMenuItem();
        MenuItem close = menuPaneController.getCloseMenuItem();
        MenuItem toTui = menuPaneController.getToTuiMenuItem();

        openFile.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("MP3", "*.mp3"));
            File file = fileChooser.showOpenDialog(new Stage());

            try {
                contentPaneController.getContentTable().getItems().add(Mp3Parser.createMp3Song(file));
                showMessage("Wczytano dane z pliku: " +  file.getName());
            } catch (Exception e) {
                showMessage("Wystąpił błąd podczas wczytywania pliku: " + file.getName());
            }

        });

        openDir.setOnAction(actionEvent -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File dir = directoryChooser.showDialog(new Stage());

            try {
                contentPaneController.getContentTable().getItems().addAll(Mp3Parser.createMp3List(dir));
                showMessage("Wczytano dane z folderu: " + dir.getName());
            } catch (Exception e) {
                showMessage("Wystąpił błąd podczas wczytywania folderu");
            }
        });

        close.setOnAction(actionEvent -> {
            System.exit(0);
        });
    }

    public void showMessage(String message){
        controlPaneController.getMessageTextField().setText(message);
    }
}
