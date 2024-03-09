package org.proyecto;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GestorJSON {
    public void guardarJSON(String path, DatosSalida datos) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(datos);

        File file = new File("data/" + path);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(json);
        }

    }
}
