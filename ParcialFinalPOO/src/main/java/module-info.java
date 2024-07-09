module org.example.parcialfinalpoo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.swing;
    requires javafx.media;
    requires java.sql;


    opens org.example.parcialfinalpoo.Clases to javafx.base;
    exports org.example.parcialfinalpoo;
    opens org.example.parcialfinalpoo to javafx.base, javafx.fxml;
}