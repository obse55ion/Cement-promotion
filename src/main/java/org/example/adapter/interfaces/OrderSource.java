package org.example.adapter.interfaces;

import org.example.entity.Order;

import java.util.List;

public interface OrderSource {

    List<Order> readOrders();

}
