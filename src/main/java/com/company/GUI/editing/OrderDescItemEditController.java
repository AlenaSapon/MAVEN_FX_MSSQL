package com.company.GUI.editing;


import com.company.dao.edao.SupplyDaoImpl;
import com.company.model.entity.Supply;
import com.company.model.items.OrderDescItem;
import com.company.model.items.OrderItem;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class OrderDescItemEditController implements Initializable {

    public ComboBox<Supply.InnerCodeDesc> innerCode;
    public TextField vendCode;
    public TextField supplyDesc;
    public TextField price;
    public TextField qty;

    public TextField amount;
    @FXML
    private Button submitButton;

    public Runnable runnable;
    private OrderDescItem orderDescItem;

    public void setItem(OrderDescItem orderDescItem, Runnable runnable){
        this.orderDescItem=orderDescItem;
        this.runnable=runnable;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Supply.InnerCodeDesc> list=new SupplyDaoImpl().getInnerCodesWithDesc();
        innerCode.setItems(FXCollections.observableArrayList(list));
        list.forEach(innerCodeDesc -> {
            if (innerCodeDesc.innerCode()==orderDescItem.getInnerCode()) innerCode.getSelectionModel().select(innerCodeDesc);
        });
        vendCode.setText(String.valueOf(orderDescItem.getVendCode()));
        supplyDesc.setText(orderDescItem.getSupplyDesc());
        Optional.ofNullable(orderDescItem.getPrice()).ifPresentOrElse(bigDecimal ->price.setText(bigDecimal.toPlainString()), ()->{price.setText("No");});
        qty.setText(String.valueOf(orderDescItem.getQty()));
        Optional.ofNullable(orderDescItem.getAmount()).ifPresentOrElse(bigDecimal ->amount.setText(bigDecimal.toPlainString()), ()->{amount.setText("No");});
        submitButton.setOnAction(actionEvent -> handleSubmitButton());

    }


    public void handleSubmitButton() {
        try {
            orderDescItem.setInnerCode(String.valueOf(innerCode.getSelectionModel().getSelectedItem().innerCode()));
            orderDescItem.setVendCode(vendCode.getText());
            orderDescItem.setSupplyDesc(supplyDesc.getText());
            orderDescItem.setPrice(price.getText());
            orderDescItem.setQty(qty.getText());
            orderDescItem.setAmount(amount.getText());
            runnable.run();
        } catch (NullPointerException | IllegalArgumentException exception) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Some field is empty");
            alert.showAndWait();
            runnable.run();
        }
    }


}
