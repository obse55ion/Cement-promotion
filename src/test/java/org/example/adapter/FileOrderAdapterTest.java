
package org.example.adapter;

import org.example.entity.Order;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileOrderAdapterTest {

    @Test
    void readsOrdersFromTxtFile() throws Exception {
        PipeFileOrderAdapter adapter =
                new PipeFileOrderAdapter();

        List<Order> orders =
                adapter.readOrders("discount_day.txt");

        assertEquals(13, orders.size());

        assertEquals("Industrial",
                orders.getFirst().getCustomerName());

        assertEquals(8800,
                orders.getFirst().getQuantity());
    }

    @Test
    void readsOrdersFromFileWithoutExtension()
            throws Exception {

        HashFileOrderAdapter adapter =
                new HashFileOrderAdapter();

        List<Order> orders =
                adapter.readOrders("discount_day_without_ext");

        assertEquals(13, orders.size());

        assertEquals("Industrial",
                orders.getFirst().getCustomerName());

        assertEquals(8800,
                orders.getFirst().getQuantity());
    }

    @Test
    void bothAdaptersReadSameNumberOfOrders()
            throws Exception {

        PipeFileOrderAdapter pipeAdapter =
                new PipeFileOrderAdapter();

        HashFileOrderAdapter hashAdapter =
                new HashFileOrderAdapter();

        List<Order> pipeOrders =
                pipeAdapter.readOrders("discount_day.txt");

        List<Order> hashOrders =
                hashAdapter.readOrders("discount_day_without_ext");

        assertEquals(13, pipeOrders.size());
        assertEquals(13, hashOrders.size());
        assertEquals(pipeOrders.size(), hashOrders.size());
    }

    @Test
    void throwsExceptionWhenFileDoesNotExist() {

        PipeFileOrderAdapter adapter =
                new PipeFileOrderAdapter();

        assertThrows(
                FileNotFoundException.class,
                () -> adapter.readOrders("missing.txt")
        );
    }
}
