module de.hitec.nhplus {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;
    requires javafx.graphics;
    requires bcrypt;
    requires org.xerial.sqlitejdbc;
    opens de.hitec.nhplus to javafx.fxml;
    opens de.hitec.nhplus.controller to javafx.fxml;
    opens de.hitec.nhplus.model to javafx.base;

    exports de.hitec.nhplus;
    exports de.hitec.nhplus.controller;
    exports de.hitec.nhplus.model;
}