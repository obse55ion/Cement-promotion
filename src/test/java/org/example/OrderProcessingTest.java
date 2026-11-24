
package org.example;

import org.example.adapter.AbstractFileOrderAdapter;
import org.example.adapter.HashFileOrderAdapter;
import org.example.adapter.PipeFileOrderAdapter;
import org.example.entity.Order;
import org.example.service.SaleService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class OrderProcessingTest {

    @Test
    void processesBothResourceFilesCorrectly() throws Exception {
        PipeFileOrderAdapter txtAdapter =
                new PipeFileOrderAdapter();

        HashFileOrderAdapter hashAdapter =
                new HashFileOrderAdapter();

        List<Order> orders = new ArrayList<>();

        orders.addAll(
                txtAdapter.readOrders(
                        Path.of("src/main/resources/discount_day.txt")
                )
        );

        orders.addAll(
                hashAdapter.readOrders(
                        Path.of("src/main/resources/discount_day_without_ext")
                )
        );

        assertEquals(26, orders.size());

        SaleService service = new SaleService(
                new BigDecimal("10"),
                20,
                5
        );

        Map<String, BigDecimal> results =
                service.processOrder(orders);

        assertEquals(12, results.size());

        assertEquals(new BigDecimal("187110.00"), results.get("Recovery"));
        assertEquals(new BigDecimal("815380.00"), results.get("Power Engineer"));
        assertEquals(new BigDecimal("428200.00"), results.get("Preparatory"));
        assertEquals(new BigDecimal("202000.00"), results.get("Pyramid"));
        assertEquals(new BigDecimal("392000.00"), results.get("Fossil"));
        assertEquals(new BigDecimal("662400.00"), results.get("Mosque"));
        assertEquals(new BigDecimal("250000.00"), results.get("Atomic"));
        assertEquals(new BigDecimal("73600.00"), results.get("Electricity"));
        assertEquals(new BigDecimal("176000.00"), results.get("Industrial"));
        assertEquals(new BigDecimal("593400.00"), results.get("Carryover"));
        assertEquals(new BigDecimal("112200.00"), results.get("Resident"));
        assertEquals(new BigDecimal("600000.00"), results.get("Ancillary"));
    }
}
