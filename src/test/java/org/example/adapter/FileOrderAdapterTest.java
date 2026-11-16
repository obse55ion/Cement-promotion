
package org.example.adapter;

import org.example.entity.Order;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileOrderAdapterTest {

    @Test
    void readsOrdersFromTxtFile() throws Exception {
        FileOrderAdapter adapter =
                new FileOrderAdapter("\\|");

        List<Order> orders =
                adapter.readOrders("discount_day.txt");

        assertEquals(13, orders.size());

        assertEquals(
                "Industrial",
                orders.get(0).getCustomerName()
        );

        assertEquals(
                8800,
                orders.get(0).getQuantity()
        );
    }

    @Test
    void readsOrdersFromFileWithoutExtension()
            throws Exception {

        FileOrderAdapter adapter =
                new FileOrderAdapter("#");

        List<Order> orders =
                adapter.readOrders("discount_day_without_ext");

        assertEquals(13, orders.size());

        assertEquals(
                "Industrial",
                orders.get(0).getCustomerName()
        );

        assertEquals(
                8800,
                orders.get(0).getQuantity()
        );
    }

    @Test
    void bothFilesContainSameNumberOfOrders()
            throws Exception {

        FileOrderAdapter txtAdapter =
                new FileOrderAdapter("\\|");

        FileOrderAdapter hashAdapter =
                new FileOrderAdapter("#");

        List<Order> txtOrders =
                txtAdapter.readOrders("discount_day.txt");

        List<Order> hashOrders =
                hashAdapter.readOrders("discount_day_without_ext");

        assertEquals(txtOrders.size(), hashOrders.size());
    }

    @Test
    void throwsExceptionWhenFileDoesNotExist() {

        FileOrderAdapter adapter =
                new FileOrderAdapter("\\|");

        assertThrows(
                java.io.FileNotFoundException.class,
                () -> adapter.readOrders("missing.txt")
        );
    }
}