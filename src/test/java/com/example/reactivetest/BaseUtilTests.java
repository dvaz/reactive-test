package com.example.reactivetest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.util.List;

public class BaseUtilTests {

    @Autowired
    private ObjectMapper objectMapper;

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
