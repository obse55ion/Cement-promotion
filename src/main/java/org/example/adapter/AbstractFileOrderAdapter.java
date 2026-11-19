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
import java.util.Objects;
import java.util.regex.Pattern;

public abstract class AbstractFileOrderAdapter implements OrderSource {

    private final String separator;

    public AbstractFileOrderAdapter(String separator) {
        this.separator = Objects.requireNonNull(
                separator, "Разделитель не может быть null"
        );

        if (separator.isEmpty()) {
            throw new IllegalArgumentException(
                    "Разделитель не может быть пустым"
            );
        }
    }

    @Override
    public List<Order> readOrders(Path filePath) throws IOException {
        Objects.requireNonNull(filePath, "Путь к файлу не может быть null");
        List<Order> orders = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(
                filePath, StandardCharsets.UTF_8)) {

            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;

                if (line.isBlank()) {
                    continue;
                }

                orders.add(parseOrder(line, lineNumber));
            }
        }

        return orders;
    }

    private Order parseOrder(String line, int lineNumber) {
        String[] parts = line.split(Pattern.quote(separator), -1);

        if (parts.length != 3) {
            throw new IllegalArgumentException(
                    "Некорректная строка " + lineNumber + ": " + line
            );
        }

        try {
            LocalDateTime orderTime = LocalDateTime.parse(parts[0].trim());
            String customerName = parts[1].trim();
            int quantity = Integer.parseInt(parts[2].trim());

            return new Order(orderTime, quantity, customerName);

        } catch (RuntimeException exception) {
            throw new IllegalArgumentException(
                    "Ошибка при обработке строки " + lineNumber + ": " + line,
                    exception
            );
        }

    }
}