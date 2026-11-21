
package org.example.adapter;

import org.example.entity.Order;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileOrderAdapterTest {

    @Test
    void readsOrdersFromTxtFile() throws Exception {

        PipeFileOrderAdapter adapter =
                new PipeFileOrderAdapter();

        Path filePath = Path.of(
                getClass().getResource("/discount_day.txt").toURI()
        );

        List<Order> orders =
                adapter.readOrders(filePath);

        assertEquals(13, orders.size());

        assertEquals(
                "Industrial",
                orders.getFirst().getCustomerName()
        );

        assertEquals(
                8800,
                orders.getFirst().getQuantity()
        );
    }

    @Test
    void readsOrdersFromFileWithoutExtension()
            throws Exception {

        HashFileOrderAdapter adapter =
                new HashFileOrderAdapter();

        Path filePath = Path.of(
                getClass().getResource("/discount_day_without_ext").toURI()
        );

        List<Order> orders =
                adapter.readOrders(filePath);

        assertEquals(13, orders.size());

        assertEquals(
                "Industrial",
                orders.getFirst().getCustomerName()
        );

        assertEquals(
                8800,
                orders.getFirst().getQuantity()
        );
    }

    @Test
    void bothAdaptersReadSameNumberOfOrders()
            throws Exception {

        PipeFileOrderAdapter pipeAdapter =
                new PipeFileOrderAdapter();

        HashFileOrderAdapter hashAdapter =
                new HashFileOrderAdapter();

        Path txtPath = Path.of(
                getClass().getResource("/discount_day.txt").toURI()
        );

        Path hashPath = Path.of(
                getClass().getResource("/discount_day_without_ext").toURI()
        );

        List<Order> pipeOrders =
                pipeAdapter.readOrders(txtPath);

        List<Order> hashOrders =
                hashAdapter.readOrders(hashPath);

        assertEquals(13, pipeOrders.size());
        assertEquals(13, hashOrders.size());

        assertEquals(
                pipeOrders.size(),
                hashOrders.size()
        );
    }

    @Test
    void throwsExceptionWhenFileDoesNotExist() {

        PipeFileOrderAdapter adapter =
                new PipeFileOrderAdapter();

        assertThrows(
                FileNotFoundException.class,
                () -> adapter.readOrders(
                        Path.of("missing.txt")
                )
        );
    }
}
