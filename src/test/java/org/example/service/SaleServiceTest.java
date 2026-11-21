
package org.example.service;

import org.example.adapter.HashFileOrderAdapter;
import org.example.adapter.PipeFileOrderAdapter;
import org.example.entity.Order;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SaleServiceTest {

    @Test
    void correctlyCalculatesPricesForRealOrders()
            throws Exception {

        PipeFileOrderAdapter txtAdapter =
                new PipeFileOrderAdapter();

        HashFileOrderAdapter hashAdapter =
                new HashFileOrderAdapter();

        Path txtPath = Path.of(
                getClass().getResource("/discount_day.txt").toURI()
        );

        Path hashPath = Path.of(
                getClass().getResource("/discount_day_without_ext").toURI()
        );

        List<Order> orders = new ArrayList<>();

        orders.addAll(txtAdapter.readOrders(txtPath));
        orders.addAll(hashAdapter.readOrders(hashPath));

        orders = orders.stream()
                .sorted(Comparator.comparing(Order::getOrderTime))
                .toList();

        SaleService service = new SaleService(
                new BigDecimal("10"),
                new BigDecimal("0.20"),
                new BigDecimal("0.05")
        );

        Map<String, BigDecimal> result =
                service.processOrder(new ArrayList<>(orders));

        assertEquals(12, result.size());

        assertEquals(new BigDecimal("187110.00"), result.get("Recovery"));
        assertEquals(new BigDecimal("815380.00"), result.get("Power Engineer"));
        assertEquals(new BigDecimal("428200.00"), result.get("Preparatory"));
        assertEquals(new BigDecimal("202000.00"), result.get("Pyramid"));
        assertEquals(new BigDecimal("392000.00"), result.get("Fossil"));
        assertEquals(new BigDecimal("662400.00"), result.get("Mosque"));
        assertEquals(new BigDecimal("250000.00"), result.get("Atomic"));
        assertEquals(new BigDecimal("73600.00"), result.get("Electricity"));
        assertEquals(new BigDecimal("176000.00"), result.get("Industrial"));
        assertEquals(new BigDecimal("593400.00"), result.get("Carryover"));
        assertEquals(new BigDecimal("112200.00"), result.get("Resident"));
        assertEquals(new BigDecimal("600000.00"), result.get("Ancillary"));
    }

    @Test
    void returnsEmptyMapWhenThereAreNoOrders() {

        SaleService service = new SaleService(
                new BigDecimal("10"),
                new BigDecimal("0.20"),
                new BigDecimal("0.05")
        );
        Map<String, BigDecimal> result =
                service.processOrder(new ArrayList<>());;

        assertTrue(result.isEmpty());
    }

    @Test
    void appliesZeroDiscountAfterDiscountRunsOut() {

        SaleService service = new SaleService(
                new BigDecimal("100"),
                new BigDecimal("0.10"),
                new BigDecimal("0.10")
        );

        List<Order> orders = new ArrayList<>(List.of(
                new Order(
                        LocalDateTime.parse("2021-02-09T08:00:00"),
                        100,
                        "Company A"
                ),
                new Order(
                        LocalDateTime.parse("2021-02-09T09:00:00"),
                        100,
                        "Company B"
                ),
                new Order(
                        LocalDateTime.parse("2021-02-09T10:00:00"),
                        100,
                        "Company C"
                )
        ));

        Map<String, BigDecimal> result = service.processOrder(orders);

        assertEquals(new BigDecimal("9000.00"), result.get("Company A"));
        assertEquals(new BigDecimal("10000.00"), result.get("Company B"));
        assertEquals(new BigDecimal("10000.00"), result.get("Company C"));
    }
}
