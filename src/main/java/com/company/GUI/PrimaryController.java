package com.company.GUI;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.company.dao.edao.OrderDaoImpl;
import com.company.model.entity.Order;
import javafx.fxml.FXML;

public class PrimaryController {



    public void handleConnect() throws SQLException {
        List<Order> arrayList=new OrderDaoImpl().findAll();
        for (Order order:arrayList) {
            System.out.println(order);
        }
        System.out.println(new OrderDaoImpl().calculateAmount(1000));
    }

    @FXML
    private void handleWorkWithOrders() throws IOException  {
        App.setRoot("secondary");
    }


}
