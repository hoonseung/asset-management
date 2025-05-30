package com.sewon.common.translator;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Map;
import org.springframework.core.io.ClassPathResource;

public class JsonEnumTranslator {

    private JsonEnumTranslator() {
    }

    public static String translate(String name, String key, String nation) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String path = String.format("/translation/%s/%s_%s.json", nation, name, nation);
        Map<String, String> langStore = mapper.readValue(
            new ClassPathResource(path).getInputStream(), new TypeReference<>() {
            });

        return langStore.get(key);
    }
}
