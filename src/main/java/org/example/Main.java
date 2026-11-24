package org.example;

import org.example.adapter.HashFileOrderAdapter;
import org.example.adapter.PipeFileOrderAdapter;
import org.example.adapter.interfaces.OrderSource;
import org.example.entity.Order;
import org.example.repository.ResultWriter;
import org.example.repository.interfaces.ResultOutput;
import org.example.service.SaleService;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class Main {
    static void main() throws IOException {
        OrderSource txtReader = new PipeFileOrderAdapter();
        OrderSource hashReader = new HashFileOrderAdapter();

        List<Order> txtOrders = txtReader.readOrders(
                Path.of("src/main/resources/discount_day.txt")
        );
        List<Order> hashOrders = hashReader.readOrders(
                Path.of("src/main/resources/discount_day_without_ext")
        );
        SaleService saleService = new SaleService(
                BigDecimal.valueOf(10),
                20,
                5
        );

        Map<String, BigDecimal> txtResults =
                saleService.processOrder(txtOrders);
        Map<String, BigDecimal> hashResults =
                saleService.processOrder(hashOrders);

        ResultOutput writer = new ResultWriter();

        writer.writeResults(
                txtResults,
                Path.of("result_txt.txt")
        );

        writer.writeResults(
                hashResults,
                Path.of("result_hash.txt")
        );
    }

}
