module com.baza.digitalsecretary {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;


    opens com.baza.digitalsecretary to javafx.fxml;
    exports com.baza.digitalsecretary;
}