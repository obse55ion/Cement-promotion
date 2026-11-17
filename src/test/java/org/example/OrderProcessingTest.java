
package org.example;

import org.example.adapter.AbstractFileOrderAdapter;
import org.example.entity.Order;
import org.example.service.SaleService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class OrderProcessingTest {

    @Test
    void processesBothResourceFilesCorrectly()
            throws Exception {

        // Создаём адаптеры
        AbstractFileOrderAdapter txtAdapter =
                new AbstractFileOrderAdapter("\\|");

        AbstractFileOrderAdapter hashAdapter =
                new AbstractFileOrderAdapter("#");

        // Читаем заказы
        List<Order> allOrders = new ArrayList<>();

        allOrders.addAll(
                txtAdapter.readOrders("discount_day.txt")
        );

        allOrders.addAll(
                hashAdapter.readOrders("discount_day_without_ext")
        );

        // Проверяем количество заказов
        assertEquals(26, allOrders.size());

        // Сортируем общую очередь
        allOrders.sort(
                Comparator.comparing(Order::getOrderTime)
        );

        // Первый заказ должен быть Recovery
        assertEquals(
                "Recovery",
                allOrders.get(0).getCustomerName()
        );

        // Проверяем порядок времени
        for (int i = 1; i < allOrders.size(); i++) {
            assertFalse(
                    allOrders.get(i).getOrderTime()
                            .isBefore(
                                    allOrders.get(i - 1).getOrderTime()
                            )
            );
        }

        // Рассчитываем стоимость
        SaleService service =
                new SaleService(10, 20, 5);

        Map<String, Integer> results =
                service.processOrder(allOrders);

        // Проверяем итог
        assertEquals(12, results.size());

        assertEquals(187110, results.get("Recovery"));
        assertEquals(815380, results.get("Power Engineer"));
    }
}