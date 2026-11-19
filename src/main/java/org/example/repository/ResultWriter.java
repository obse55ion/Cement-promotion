package org.example.repository;

import org.example.repository.interfaces.ResultOutput;

import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;

public class ResultWriter implements ResultOutput {

    @Override
    public void writeResults(Map<String, BigDecimal> results, Path filePath) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(
                filePath, StandardCharsets.UTF_8)) {

            for (Map.Entry<String, BigDecimal> entry
                    : new TreeMap<>(results).entrySet()) {

                writer.write(
                        entry.getKey() + " - " + entry.getValue()
                );

                writer.newLine();
            }
        }
    }
}
