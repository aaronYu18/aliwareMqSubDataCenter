package com.courier.commons.util.json;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by ryan on 16/2/23.
 */
public class JacksonUtil {

    private static ObjectMapper mapper = new ObjectMapper();

    public static ObjectMapper buildJackson(){
        return mapper;
    }

    public static String toJson(Object object){
        try {
            return mapper.writeValueAsString(object);
        } catch (Exception e){
            return null;
        }
    }

    public static <T> T getBean(String json, Class<T> cls) {
        try {
            return mapper.readValue(json, cls);
        } catch (IOException e) {
            return null;
        }
    }
}
