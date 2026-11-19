package org.example.repository.interfaces;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.Map;

public interface ResultOutput {

    void writeResults(
            Map<String, BigDecimal> results,
            Path filePath
    ) throws IOException;
}
