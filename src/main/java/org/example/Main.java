package org.example;

import org.example.adapter.FileOrderAdapter;
import org.example.adapter.interfaces.OrderSource;
import org.example.entity.Order;
import org.example.repository.ResultWriter;
import org.example.service.SaleService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Main {
    static void main() throws IOException {
        OrderSource txtReader = new FileOrderAdapter("\\|");
        OrderSource hashReader = new FileOrderAdapter("#");

        List<Order> txtOrders = txtReader.readOrders("discount_day.txt");
        List<Order> hashOrders = hashReader.readOrders("discount_day_without_ext");

        List<Order> allOrders = new ArrayList<>();
        allOrders.addAll(txtOrders);
        allOrders.addAll(hashOrders);

        allOrders.sort(Comparator.comparing(Order::getOrderTime));

        SaleService saleService = new SaleService(10, 20, 5);

        Map<String, Integer> results = saleService.processOrder(allOrders);

        ResultWriter writer = new ResultWriter();
        writer.writeResults(results, "result.txt");
    }

}
