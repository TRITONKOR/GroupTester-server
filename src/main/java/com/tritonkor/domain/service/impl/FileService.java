package com.tritonkor.domain.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.stereotype.Service;

/**
 * Service class for file operations.
 */
@Service
public class FileService {

    /**
     * Gets the path of a resource.
     *
     * @param resourceName The name of the resource.
     * @return The path of the resource.
     * @throws IOException if an I/O error occurs.
     */
    public Path getPathFromResource(String resourceName) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        Path tempFile = Files.createTempFile("temp", null);
        try (InputStream inputStream = classLoader.getResourceAsStream(resourceName)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Resource not found: " + resourceName);
            }
            Files.copy(inputStream, tempFile, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        }
        return tempFile;
    }

    /**
     * Reads bytes from a file.
     *
     * @param path The path of the file.
     * @return The bytes read from the file.
     * @throws IOException if an I/O error occurs.
     */
    public byte[] getBytes(Path path) throws IOException {
        return Files.readAllBytes(path);
    }
}
