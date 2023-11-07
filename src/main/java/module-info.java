module MP3 {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.media;
    requires jid3lib;

    exports org.example.main to javafx.graphics;
    opens org.example.controller to javafx.fxml;
    opens org.example.mp3 to javafx.base;
}