package com.kata.cervezas.service;

import com.kata.cervezas.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${app.upload.dir:uploads/images}")
    private String uploadDir;

    public String storeFile(MultipartFile file, Integer beerId) {
        try {
            // Crear directorio si no existe
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Generar nombre único para el archivo
            String originalFilename = file.getOriginalFilename();
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String newFilename = "beer_" + beerId + "_" + UUID.randomUUID().toString() + extension;

            // Guardar archivo
            Path filePath = uploadPath.resolve(newFilename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return newFilename;
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar el archivo: " + e.getMessage(), e);
        }
    }

    public byte[] loadFile(String filename) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(filename);
            if (!Files.exists(filePath)) {
                throw new ResourceNotFoundException("Archivo no encontrado: " + filename);
            }
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo: " + e.getMessage(), e);
        }
    }

    public void deleteFile(String filename) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(filename);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Error al eliminar el archivo: " + e.getMessage(), e);
        }
    }
}
