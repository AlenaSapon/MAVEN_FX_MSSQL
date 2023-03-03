package com.company.model.items;

import com.company.GUI.App;
import com.company.GUI.editing.OrderItemEditController;
import com.company.dao.edao.OrderDaoImpl;
import com.company.model.entity.Entity;
import com.company.model.entity.Order;
import javafx.beans.property.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;


public class OrderItem extends EntityItem<Integer> {


    private int syntCode;
    private final IntegerProperty orderNum;
    private final StringProperty supplier;
    private final ObjectProperty<java.sql.Date> orderDate;
    private final IntegerProperty staffCode;
    private final ObjectProperty<BigDecimal> amount;


    public OrderItem(Order order){
        this.syntCode=order.getSyntCode();
        orderNum=new SimpleIntegerProperty(order.getOrderNum());
        supplier=new SimpleStringProperty(Optional.ofNullable(order.getSupplier()).orElse("None"));
        orderDate =new SimpleObjectProperty<>(Optional.ofNullable(order.getDate()).orElse(new Date(0)));
        staffCode=new SimpleIntegerProperty(order.getStaffCode());
        amount=new SimpleObjectProperty<>(Optional.ofNullable(order.getAmount()).orElse(new BigDecimal("0.00")));
        super.entityStatus=order.getEntityStatus();
    }



    public Integer getOrderNum() {
        return orderNum.get();
    }

    public void setOrderNum(String orderNum) throws IllegalArgumentException{
        Scanner scanner=new Scanner(orderNum);
        if (scanner.hasNextInt()) {
            this.orderNum.set(scanner.nextInt());
        }
        else {
            throw new IllegalArgumentException(orderNum);
        }
    }

    public String getSupplier() {
        return supplier.get();
    }

    public void setSupplier(String supplier) {
        this.supplier.set(supplier);
    }

    public java.sql.Date getOrderDate() {
        return orderDate.get();
    }

    public void setOrderDate(String orderDate) throws IllegalArgumentException {
        this.orderDate.set(Date.valueOf(orderDate));
    }

    public Integer getStaffCode() {
        return staffCode.get();
    }

    public void setStaffCode(String staffCode) throws IllegalArgumentException {
        Scanner scanner=new Scanner(staffCode);
        if (scanner.hasNextInt()) {
            this.staffCode.set(scanner.nextInt());
        }
        else {
            throw new IllegalArgumentException(staffCode);
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
    public void performOperation() {
        Order order = new Order();
        order.setSyntCode(syntCode);
        order.setOrderNum(getOrderNum());
        order.setSupplier(getSupplier());
        order.setDate(getOrderDate());
        order.setStaffCode(getStaffCode());
        order.setAmount(getAmount());
        order.setEntityStatus(entityStatus);
        try {
            switch (order.getEntityStatus()) {
                case INSERT -> new OrderDaoImpl().create(order);
                case DELETE -> new OrderDaoImpl().deleteWithDescription(order.getKey());
                case UPDATE -> new OrderDaoImpl().update(order);
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }

    }

    public HBox getEditWindow(Runnable runnable)  {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("orderItemEdit.fxml"));
        OrderItemEditController controller=new OrderItemEditController();
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
    public Integer getKey() {
        return syntCode;
    }
}
