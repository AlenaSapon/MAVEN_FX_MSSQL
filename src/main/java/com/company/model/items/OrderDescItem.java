package com.company.model.items;

import com.company.GUI.App;
import com.company.GUI.editing.OrderDescItemEditController;
import com.company.GUI.editing.OrderItemEditController;
import com.company.dao.edao.OrderDaoImpl;
import com.company.dao.edao.OrderDescDaoImpl;
import com.company.model.entity.Entity;
import com.company.model.entity.OrderDesc;
import javafx.beans.property.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;

public class OrderDescItem extends EntityItem<OrderDesc.CompoundKey> {

    private OrderDesc.CompoundKey initialCompoundKey;


    private final IntegerProperty innerCode;
    private final IntegerProperty vendCode;
    private final StringProperty supplyDesc;
    private final ObjectProperty<BigDecimal> price;
    private final IntegerProperty qty;
    private final ObjectProperty<BigDecimal> amount;

    public OrderDescItem(OrderDesc orderDesc) {
        this.initialCompoundKey=orderDesc.getCompoundKey();
        this.innerCode=new SimpleIntegerProperty(orderDesc.getCompoundKey().getInnerCode());
        this.vendCode=new SimpleIntegerProperty(orderDesc.getCompoundKey().getVendCode());
        this.supplyDesc=new SimpleStringProperty(Optional.ofNullable(orderDesc.getSupplyDesc()).orElse("None"));
        this.price=new SimpleObjectProperty<>(Optional.ofNullable(orderDesc.getPrice()).orElse(new BigDecimal("0.00")));
        this.qty=new SimpleIntegerProperty(orderDesc.getQty());
        this.amount=new SimpleObjectProperty<>(Optional.ofNullable(orderDesc.getAmount()).orElse(new BigDecimal("0.00")));
        super.entityStatus=orderDesc.getEntityStatus();
    }

    public int getInnerCode() {
        return innerCode.get();
    }

    public void setInnerCode(String innerCode) {
        Scanner scanner=new Scanner(innerCode);
        if (scanner.hasNextInt()) {
            this.innerCode.set(scanner.nextInt());
        }
        else {
            throw new IllegalArgumentException(innerCode);
        }
    }

    public int getVendCode() {
        return vendCode.get();
    }

    public void setVendCode(String vendCode) {
        Scanner scanner=new Scanner(vendCode);
        if (scanner.hasNextInt()) {
            this.vendCode.set(scanner.nextInt());
        }
        else {
            throw new IllegalArgumentException(vendCode);
        }
    }

    public String getSupplyDesc() {
        return supplyDesc.get();
    }

    public void setSupplyDesc(String supplyDesc) {
        this.supplyDesc.set(supplyDesc);
    }

    public BigDecimal getPrice() {
        return price.get();
    }

    public void setPrice(String price) {
        Scanner scanner=new Scanner(price);
        if (scanner.hasNextBigDecimal()) {
            this.price.set(scanner.nextBigDecimal());
        }
        else {
            throw new IllegalArgumentException(price);
        }
    }

    public int getQty() {
        return qty.get();
    }

    public void setQty(String qty) {
        Scanner scanner=new Scanner(qty);
        if (scanner.hasNextInt()) {
            this.qty.set(scanner.nextInt());
        }
        else {
            throw new IllegalArgumentException(qty);
        }
    }

    public BigDecimal getAmount() {
        return amount.get();
    }

    public void setAmount(String amount) throws IllegalArgumentException {
        Scanner scanner=new Scanner(amount);
        if (scanner.hasNextBigDecimal()) {
            this.amount.set(scanner.nextBigDecimal());
        }
        else {
            throw new IllegalArgumentException(amount);
        }
    }
    @Override
    public void performOperation(){
        OrderDesc orderDesc =new OrderDesc();
        orderDesc.getCompoundKey().setSyntCode(initialCompoundKey.getSyntCode());
        orderDesc.getCompoundKey().setInnerCode(innerCode.get());
        orderDesc.getCompoundKey().setVendCode(vendCode.get());
        orderDesc.setSupplyDesc(supplyDesc.get());
        orderDesc.setPrice(price.get());
        orderDesc.setQty(qty.get());
        orderDesc.setAmount(amount.get());
        orderDesc.setEntityStatus(entityStatus);
        try {
            switch (orderDesc.getEntityStatus()) {
                case INSERT -> new OrderDescDaoImpl().create(orderDesc);
                case DELETE -> new OrderDescDaoImpl().delete(initialCompoundKey);
                case UPDATE -> new OrderDescDaoImpl().update(orderDesc,initialCompoundKey);
            }
        } catch (
                SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
    }




    public HBox getEditWindow(Runnable runnable)  {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("orderIDescItemEdit.fxml"));
        OrderDescItemEditController controller=new OrderDescItemEditController();
        controller.setItem(this,runnable);
        fxmlLoader.setController(controller);
        HBox hBox= null;
        try {
            hBox = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return hBox;
    }

    @Override
    public OrderDesc.CompoundKey getKey() {
        return initialCompoundKey;
    }
}
