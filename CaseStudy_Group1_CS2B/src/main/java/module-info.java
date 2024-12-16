module com.example.casestudy_group1_cs2b {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.casestudy_group1_cs2b to javafx.fxml;
    exports com.example.casestudy_group1_cs2b;
}