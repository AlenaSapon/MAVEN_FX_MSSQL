package com.company.GUI.editing;


import com.company.GUI.SecondaryController;
import com.company.dao.edao.EmployeeDaoImpl;
import com.company.dao.idao.EmployeeDao;
import com.company.model.entity.Entity;
import com.company.model.items.OrderItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class OrderItemEditController implements Initializable {
    public TextField orderNumber;
    public TextField supplier;
    public TextField orderDate;
    public ComboBox<Integer> staffCode;
    public TextField amount;
    public HBox mainBox;
    @FXML
    private Button submitButton;

    public Runnable runnable;
    private OrderItem orderItem;

    public void setItem(OrderItem orderItem, Runnable runnable){
        this.orderItem=orderItem;
        this.runnable=runnable;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        orderNumber.setText(String.valueOf(orderItem.getOrderNum()));
        supplier.setText(orderItem.getSupplier());
        orderDate.setText(orderItem.getOrderDate().toString());
        List<Integer> list = new  EmployeeDaoImpl().getAllStaffCodes();
        staffCode.setItems(FXCollections.observableArrayList(list));
        if (list.contains(orderItem.getStaffCode())) staffCode.getSelectionModel().select(orderItem.getStaffCode());
        Optional.ofNullable(orderItem.getAmount()).ifPresentOrElse(bigDecimal ->amount.setText(bigDecimal.toPlainString()), ()->{amount.setText("Null");});
        submitButton.setOnAction(actionEvent -> handleSubmitButton());


    }


    public void handleSubmitButton() {
        try {
            orderItem.setOrderNum(orderNumber.getText());
            orderItem.setSupplier(supplier.getText());
            orderItem.setOrderDate(orderDate.getText());
            orderItem.setStaffCode(staffCode.getSelectionModel().getSelectedItem().toString());
            orderItem.setAmount(amount.getText());
            orderItem.setEntityStatus(Entity.EntityStatus.UPDATE);
            runnable.run();
        } catch (NullPointerException | IllegalArgumentException exception) {
            Alert alert =new Alert(Alert.AlertType.WARNING, "Some field is empty");
            alert.showAndWait();
            runnable.run();
        }

    }


}
