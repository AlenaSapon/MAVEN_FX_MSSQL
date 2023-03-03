module com.company {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.microsoft.sqlserver.jdbc;


    exports com.company.dao;
    exports com.company.GUI;
    opens com.company.GUI to javafx.fxml;
    opens com.company.GUI.editing to javafx.fxml;
    exports com.company.dao.idao;
    exports com.company.model.entity;
    exports com.company.model.items;
    exports com.company.GUI.editing;
}
