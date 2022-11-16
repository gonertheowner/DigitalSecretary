module com.baza.digitalsecretary {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.baza.digitalsecretary to javafx.fxml;
    exports com.baza.digitalsecretary;
}