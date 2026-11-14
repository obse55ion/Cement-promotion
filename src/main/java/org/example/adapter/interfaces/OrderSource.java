package org.example.adapter.interfaces;

import org.example.adapter.FileOrderAdapter;
import org.example.entity.Order;

import java.io.IOException;
import java.util.List;

public interface OrderSource {

    List<Order> readOrders(String filePath) throws IOException;

}
