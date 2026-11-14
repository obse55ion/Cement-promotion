package org.example.adapter;

import org.example.adapter.interfaces.OrderSource;
import org.example.entity.Order;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FileOrderAdapter implements OrderSource {

    private final String separator;

    public FileOrderAdapter(String separator) {
        this.separator = separator;
    }

    @Override
    public List<Order> readOrders(String filePath) throws IOException {
        List<String> lines = loadLines(filePath);
        List<Order> orders = new ArrayList<>();
        for (String line : lines) {
            if (line.isBlank()) {
                continue;
            }
            String[] parts = line.split(separator);
            if (parts.length != 3) {
                throw new IllegalArgumentException(
                        "Некорректная строка: " + line
                );
            }
            LocalDateTime orderTime = LocalDateTime.parse(parts[0]);
            String customerName = parts[1];
            int quantity = Integer.parseInt(parts[2]);

            Order order = new Order(
                    orderTime,
                    quantity,
                    customerName
            );
            orders.add(order);
        }
        return orders;
    }

    private List<String> loadLines(String resourceName) throws IOException {

        InputStream inputStream = getClass()
                .getClassLoader()
                .getResourceAsStream(resourceName);

        if (inputStream == null) {
            throw new FileNotFoundException(
                    "Файл не найден: " + resourceName
            );
        }

        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream))) {

            String line;

            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }

        return lines;
    }


}
