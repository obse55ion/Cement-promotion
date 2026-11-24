
package org.example.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ResultWriterTest {

    @TempDir
    Path tempDir;

    @Test
    void writesResultsToFile() throws Exception {

        Path output = tempDir.resolve("result.txt");

        Map<String, BigDecimal> results = new LinkedHashMap<>();

        results.put("Recovery", new BigDecimal("187110"));
        results.put("Pyramid", new BigDecimal("202000"));

        ResultWriter writer = new ResultWriter();

        writer.writeResults(results, output);

        List<String> lines = Files.readAllLines(output);

        assertEquals(2, lines.size());

        assertTrue(lines.contains("Recovery - 187110"));
        assertTrue(lines.contains("Pyramid - 202000"));
    }

    @Test
    void createsEmptyFileForEmptyResults()
            throws Exception {

        Path output = tempDir.resolve("empty.txt");

        ResultWriter writer = new ResultWriter();

        writer.writeResults(Map.of(), Path.of(output.toString()));

        assertTrue(Files.exists(output));

        assertTrue(
                Files.readAllLines(output).isEmpty()
        );
    }
}