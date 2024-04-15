module com.example.csit228f2_2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.csit228f2_2 to javafx.fxml;
    exports com.example.csit228f2_2;
}