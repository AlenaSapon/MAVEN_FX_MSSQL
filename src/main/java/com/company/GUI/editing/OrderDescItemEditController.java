package com.company.GUI.editing;


import com.company.model.items.OrderItem;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class OrderItemEditController implements Initializable {
    public TextField orderNumber;
    public TextField supplier;
    public TextField orderDate;
    public ComboBox<String> staffCode;
    public TextField amount;
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
        staffCode.setItems(FXCollections.observableArrayList(String.valueOf(orderItem.getStaffCode())));
        staffCode.getSelectionModel().selectFirst();
        Optional.ofNullable(orderItem.getAmount()).ifPresentOrElse(bigDecimal ->amount.setText(bigDecimal.toPlainString()), ()->{amount.setText("");});
        submitButton.setOnAction(actionEvent -> handleSubmitButton());

    }


    public void handleSubmitButton() {
        orderItem.setOrderNum(orderNumber.getText());
        orderItem.setSupplier(supplier.getText());
        orderItem.setOrderDate(orderDate.getText());
        orderItem.setStaffCode(staffCode.getSelectionModel().getSelectedItem());
        orderItem.setAmount(amount.getText());
        runnable.run();
    }


}
