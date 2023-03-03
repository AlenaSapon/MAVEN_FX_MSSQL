package com.company.GUI;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.company.GUI.editing.OperationType;
import com.company.dao.edao.OrderDaoImpl;
import com.company.dao.edao.OrderDescDaoImpl;
import com.company.model.entity.Entity;
import com.company.model.entity.Order;
import com.company.model.entity.OrderDesc;
import com.company.model.items.OrderDescItem;
import com.company.model.items.OrderItem;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;

public class SecondaryController implements Initializable {

    public TableView<OrderItem> ordersTable;
    public TableColumn<OrderItem,Integer> orderNum;
    public TableColumn<OrderItem,String> supplier;
    public TableColumn<OrderItem, Date> orderDate;
    public TableColumn<OrderItem,Integer> staffCode;

    public TableColumn<OrderItem, BigDecimal> amount;
    public TableView<OrderDescItem> ordersDescriptionTable;
    public TableColumn<OrderDescItem, Integer> innerCode;
    public TableColumn<OrderDescItem, Integer> vendCode;
    public TableColumn<OrderDescItem, String> supplyDesc;
    public TableColumn<OrderDescItem, BigDecimal> price;
    public TableColumn<OrderDescItem, Integer> qty;
    public TableColumn<OrderDescItem, BigDecimal> amountDesc;
    public HBox editArea;
    public static OperationType operationType;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        operationType=OperationType.DEFAULT;
        ordersTable.setRowFactory(this::ordersTableRowFactory);
        ordersDescriptionTable.setRowFactory(this::ordersDescTableRowFactory);
    }

    private TableRow<OrderItem> ordersTableRowFactory(TableView<OrderItem> orderItemTableView) {
        TableRow<OrderItem> row=new TableRow<>() {
            @Override
            protected void updateItem(OrderItem orderItem, boolean b) {
                super.updateItem(orderItem, b);
                styleProperty().unbind();
                if (b) {
                    setStyle("");
                } else {
                    styleProperty().bind(Bindings.createStringBinding(orderItem::getStyle));
                }
            }
        };
        row.setOnMouseClicked(mouseEvent -> {
                    if (!row.isEmpty()&&mouseEvent.getButton()== MouseButton.PRIMARY) {

                        if (mouseEvent.getClickCount()==2) {
                            handleEditOrder();
                        } else if (mouseEvent.getClickCount()==1) {
                            editArea.getChildren().clear();
                            handleBrowseDescriptionForOrder(row.getItem());


                        }
                    }
                }

        );
        return row;
    }
    private TableRow<OrderDescItem> ordersDescTableRowFactory(TableView<OrderDescItem> orderItemTableView) {
        TableRow<OrderDescItem> row=new TableRow<>(){
            @Override
            protected void updateItem(OrderDescItem orderDescItem, boolean b) {
                super.updateItem(orderDescItem, b);
                styleProperty().unbind();
                if (b) {
                    setStyle("");
                } else {
                    styleProperty().bind(Bindings.createStringBinding(orderDescItem::getStyle));
                }
            }
        };
        row.setOnMouseClicked(mouseEvent -> {
                    if (!row.isEmpty()&&mouseEvent.getButton()== MouseButton.PRIMARY) {
                        if (mouseEvent.getClickCount()==2) {
                            handleEditSupply();
                        }
                    }
                }

        );
        return row;
    }

    @FXML
    private  void handleMainMenu() throws IOException {
        App.setRoot("primary");
    }

    public void handleAddNewOrder() {
        Optional.ofNullable(ordersTable.getItems()).ifPresentOrElse(
                list->{list.add(new OrderItem(new Order()));},
                ()->ordersTable.setItems(FXCollections.observableArrayList(new OrderItem(new Order()))));
        ordersTable.refresh();
    }

    public void handleDeleteOrder() {
        Optional.ofNullable(ordersTable.getSelectionModel().getSelectedItem()).ifPresentOrElse(
                orderItem -> {
                    if (orderItem.getEntityStatus().equals(Entity.EntityStatus.INSERT)) {
                        ordersTable.getItems().remove(orderItem);
                    } else {
                        int orderDescQty = new OrderDescDaoImpl().findAllByOrderKey(orderItem.getKey()).size();
                        if (orderDescQty > 0) {
                            Alert alert = new Alert(Alert.AlertType.WARNING, "Order contains " + orderDescQty + " supplies, they will also deleted");
                            alert.showAndWait();
                        }
                        orderItem.setEntityStatus(Entity.EntityStatus.DELETE);
                    }
                },
                () -> {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "No items selected");
                    alert.showAndWait();
                }
        );
        ordersTable.refresh();
    }

    public void handleAddNewSupply() {
        Optional.ofNullable(ordersTable.getSelectionModel().getSelectedItem()).ifPresentOrElse(
                orderItem -> {
                    if (orderItem.getEntityStatus().equals(Entity.EntityStatus.INSERT)) {
                        Alert alert = new Alert(Alert.AlertType.WARNING, "Can not add order description for unsaved order");
                        alert.showAndWait();
                    } else if (orderItem.getEntityStatus().equals(Entity.EntityStatus.DELETE)) {
                        Alert alert = new Alert(Alert.AlertType.WARNING, "Can not add order description for deleting order");
                        alert.showAndWait();
                    } else {
                        OrderDesc orderDesc=new OrderDesc();
                        orderDesc.setEntityStatus(Entity.EntityStatus.INSERT);
                        orderDesc.getCompoundKey().setSyntCode(orderItem.getKey());
                        Optional.ofNullable(ordersDescriptionTable.getItems()).ifPresentOrElse(
                                list->{list.add(new OrderDescItem(orderDesc));},
                                ()->ordersDescriptionTable.setItems(FXCollections.observableArrayList(new OrderDescItem(new OrderDesc()))));
                        ordersDescriptionTable.refresh();
                    }
                },
                () -> {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "No orders selected to add description");
                    alert.showAndWait();
                }
        );

    }

    public void handleDeleteSupply() {
        Optional.ofNullable(ordersDescriptionTable.getSelectionModel().getSelectedItem()).ifPresentOrElse(
                orderDescItem -> {
                    if (orderDescItem.getEntityStatus().equals(Entity.EntityStatus.INSERT)) {
                    ordersDescriptionTable.getItems().remove(orderDescItem);
                    } else {
                        orderDescItem.setEntityStatus(Entity.EntityStatus.DELETE);
                    }

                    },
                ()->{Alert alert=new Alert(Alert.AlertType.WARNING, "No items selected");
                    alert.showAndWait();
                }
        );
        ordersDescriptionTable.refresh();
    }



    public void handleSaveChanges() {
        ordersDescriptionTable.getItems().forEach(OrderDescItem::performOperation);
        ordersTable.getItems().forEach(OrderItem::performOperation);



    }

    public void handleBrowseAllOrders() throws SQLException {
        operationType=OperationType.BROWSE;
        List<Order> orders=new OrderDaoImpl().findAll();
        ArrayList<OrderItem> orderItems=new ArrayList<>();
        orders.forEach(order -> orderItems.add(new OrderItem(order)));
        ordersTable.setItems(FXCollections.observableArrayList(orderItems));
    }

    public void handleBrowseDescriptionForOrder(OrderItem orderItem)  {
        List<OrderDesc> orderDescs=new OrderDescDaoImpl().findAllByOrderKey(orderItem.getKey());
        ArrayList<OrderDescItem> orderItems=new ArrayList<>();
        orderDescs.forEach(orderDesc -> orderItems.add(new OrderDescItem(orderDesc)));
        ordersDescriptionTable.setItems(FXCollections.observableArrayList(orderItems));
    }



    public void handleOpenOrder(ActionEvent actionEvent) {

    }


    public void handleEditOrder() {
        Optional.ofNullable(ordersTable.getSelectionModel().getSelectedItem()).ifPresentOrElse(
                orderItem -> {
                    editArea.getChildren().clear();
                    editArea.getChildren().add(orderItem.getEditWindow(ordersTable::refresh));
                    double orderAmount=orderItem.getAmount().doubleValue();
                    double calculatedAmount=new OrderDaoImpl().calculateAmount(orderItem.getKey()).doubleValue();
                    if (orderAmount!=calculatedAmount){
                        Alert alert=new Alert(Alert.AlertType.INFORMATION,"Order amount is: ".
                                concat(String.valueOf(orderAmount)).concat(", sum of supplies amounts is: ").
                                concat(String.valueOf(calculatedAmount)).concat(". Values is not same.") );
                        alert.show();
                    }
                },
                ()->{Alert alert=new Alert(Alert.AlertType.WARNING, "No items selected");
                    alert.showAndWait();
                }
        );
        ordersTable.refresh();
    }

    public void handleEditSupply() {
        Optional.ofNullable(ordersDescriptionTable.getSelectionModel().getSelectedItem()).ifPresentOrElse(
                orderDescItem -> {
                    editArea.getChildren().clear();
                    editArea.getChildren().add(orderDescItem.getEditWindow(ordersDescriptionTable::refresh));

                },
                ()->{Alert alert=new Alert(Alert.AlertType.WARNING, "No items selected");
                    alert.showAndWait();
                }
        );
    }
}