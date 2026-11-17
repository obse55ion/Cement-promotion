
package org.example.service;

import org.example.adapter.AbstractFileOrderAdapter;
import org.example.adapter.HashFileOrderAdapter;
import org.example.adapter.PipeFileOrderAdapter;
import org.example.entity.Order;
import org.junit.jupiter.api.Test;

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

        List<Order> orders = new ArrayList<>();

        orders.addAll(
                txtAdapter.readOrders("discount_day.txt")
        );

        orders.addAll(
                hashAdapter.readOrders("discount_day_without_ext")
        );

        orders.sort(
                Comparator.comparing(Order::getOrderTime)
        );

        SaleService service =
                new SaleService(10, 20, 5);

        Map<String, Integer> result =
                service.processOrder(orders);

        assertEquals(12, result.size());

        assertEquals(187110, result.get("Recovery"));
        assertEquals(815380, result.get("Power Engineer"));
        assertEquals(428200, result.get("Preparatory"));
        assertEquals(202000, result.get("Pyramid"));
        assertEquals(392000, result.get("Fossil"));
        assertEquals(662400, result.get("Mosque"));
        assertEquals(250000, result.get("Atomic"));
        assertEquals(73600, result.get("Electricity"));
        assertEquals(176000, result.get("Industrial"));
        assertEquals(593400, result.get("Carryover"));
        assertEquals(112200, result.get("Resident"));
        assertEquals(600000, result.get("Ancillary"));
    }

    @Test
    void returnsEmptyMapWhenThereAreNoOrders() {

        SaleService service =
                new SaleService(10, 20, 5);

        Map<String, Integer> result =
                service.processOrder(List.of());

        assertTrue(result.isEmpty());
    }

    @Test
    void appliesZeroDiscountAfterDiscountRunsOut() {

        SaleService service =
                new SaleService(10, 10, 5);

        List<Order> orders = List.of(
                new Order(
                        java.time.LocalDateTime.parse(
                                "2021-02-09T08:00:00"
                        ),
                        100,
                        "Company A"
                ),
                new Order(
                        java.time.LocalDateTime.parse(
                                "2021-02-09T09:00:00"
                        ),
                        100,
                        "Company B"
                ),
                new Order(
                        java.time.LocalDateTime.parse(
                                "2021-02-09T10:00:00"
                        ),
                        100,
                        "Company C"
                )
        );

        Map<String, Integer> result =
                service.processOrder(orders);

        assertEquals(900, result.get("Company A"));
        assertEquals(950, result.get("Company B"));
        assertEquals(1000, result.get("Company C"));
    }
}