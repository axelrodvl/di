package co.axelrod.di;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

class PropertyParser {
    private static final String DELIMITER = "=";

    static Map<String, Object> getProperties() {
        Map<String, Object> properties = new HashMap<>();
        Map<String, String> rawProperties = parsePropertyFile();

        for(Map.Entry<String, String> rawProperty : rawProperties.entrySet()) {
            if(rawProperty.getValue() == null || rawProperty.getValue().isEmpty()) {
                properties.put(rawProperty.getKey(), null);
                continue;
            }

            if(rawProperty.getValue().equals("true") || rawProperty.getValue().equals("false")) {
                properties.put(rawProperty.getKey(), Boolean.valueOf(rawProperty.getValue()));
                continue;
            }

            if(rawProperty.getValue().matches("-?\\d+")) {
                properties.put(rawProperty.getKey(), Integer.valueOf(rawProperty.getValue()));
                continue;
            }

            if(rawProperty.getValue().matches("^-?\\d+(\\.\\d+)?$")) {
                properties.put(rawProperty.getKey(), Double.valueOf(rawProperty.getValue()));
                continue;
            }

            properties.put(rawProperty.getKey(), rawProperty.getValue());
        }

        return properties;
    }

    private static Map<String, String> parsePropertyFile() {
        try {
            Map<String, String> result = new HashMap<>();

            File propertyFile = ClassLoaderUtils.getPropertyFile();
            if(propertyFile == null) {
                return result;
            }

            BufferedReader reader = new BufferedReader(new FileReader(propertyFile));
            String property;
            while ((property = reader.readLine()) != null) {
                String[] tokens = property.split(DELIMITER);
                result.put(tokens[0], tokens[1]);
            }

            return result;
        } catch (Exception ex) {
            throw new RuntimeException("Unable to parse properties");
        }
    }
}
