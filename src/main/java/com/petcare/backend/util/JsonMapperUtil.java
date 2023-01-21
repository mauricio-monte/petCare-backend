package com.petcare.backend.util;
//
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;


public class JsonMapperUtil {

    private static ObjectMapper objectMapper;

    private static void initMapper() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }
    }

    public static <T> T fromJsonStringToObject(String json, Class<T> objClass) {
        initMapper();
        try {
            T object = objectMapper.readValue(json, objClass);
            return object;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String fromObjectToJsonString(Object obj) throws JsonProcessingException {
        initMapper();
        String json = objectMapper.writeValueAsString(obj);
        return json;
    }
}