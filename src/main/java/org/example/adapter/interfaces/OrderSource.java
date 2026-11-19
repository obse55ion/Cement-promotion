package org.example.adapter.interfaces;

import org.example.entity.Order;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface OrderSource {

    List<Order> readOrders(Path filePath) throws IOException;

}
