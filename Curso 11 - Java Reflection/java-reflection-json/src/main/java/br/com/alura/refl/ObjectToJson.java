package br.com.alura.refl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ObjectToJson {

    public String transform(Object object) {
        String result = null;

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        Map<String, Object> mapper = new HashMap<>();
        Class<?> classToBeTransformed = object.getClass();

        Arrays.stream(classToBeTransformed.getDeclaredFields()).toList().forEach(
                field -> {
                    field.setAccessible(true);
                    String key = field.getName();
                    Object value = null;
                    try {
                        value = field.get(object);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    mapper.put(key, value);
                }
        );
        //outra forma de percorrer o for sem ser com o metodo stream do java
        /*for (Field field : meuObjeto.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(meuObjeto));
        }
        String jsonResult = objectMapper.writeValueAsString(map);*/

        try {
            result = objectMapper.writeValueAsString(mapper);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }
}
