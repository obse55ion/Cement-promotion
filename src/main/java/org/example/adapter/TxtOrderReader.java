package org.example.adapter;

import org.example.adapter.interfaces.OrderSource;
import org.example.entity.Order;

import java.util.List;

public class TxtOrderReader implements OrderSource {
    @Override
    public List<Order> readOrders() {
        return List.of();
    }

}
