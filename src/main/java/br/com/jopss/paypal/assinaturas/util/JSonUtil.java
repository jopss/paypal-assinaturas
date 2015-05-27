package br.com.jopss.paypal.assinaturas.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class JSonUtil {

        private final static ObjectMapper jsonMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        public static String getJSon(Object ob){
                try {
                        return jsonMapper.writeValueAsString(ob);
                } catch (JsonProcessingException ex) {
                        throw new RuntimeException(ex);
                }
        }
        
        public static <T>T getObject(String json, Class clazz){
                try {
                        return (T) jsonMapper.readValue(json, clazz);
                } catch (IOException ex) {
                        throw new RuntimeException(ex);
                }
        }
        
}
