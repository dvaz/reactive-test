package com.example.reactivetest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.util.List;

public class BaseUtilTests {

    private static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    private final String PATH_DEFAULT = "classpath:%s.json";

    //File Json to clazz
    public Object mapperFileJsonToObject(String nameFile, Class clazz) {
        try {
            String pathFile = String.format(PATH_DEFAULT, nameFile);
            return objectMapper.readValue(ResourceUtils.getFile(pathFile), clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //File json to List<clazz>
    public List mapperFileJsonToList(String nameFile, Class clazz) {
        try {
            CollectionType javaType = objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, clazz);
            String pathFile = String.format(PATH_DEFAULT, nameFile);
            return objectMapper.readValue(ResourceUtils.getFile(pathFile), javaType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //JSON string to class
    public Object mapperJsonToObject(String json, Class clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
