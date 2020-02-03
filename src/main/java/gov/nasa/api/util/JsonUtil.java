package gov.nasa.api.util;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JsonUtil <T extends Serializable> {

	
	public static String converterObjetoParaString(Serializable objeto) throws Exception {
		try {
			
		
			ObjectMapper objectMapper = new ObjectMapper();
		    objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		    return objectMapper.writeValueAsString(objeto);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public T converterStringParaObjeto(String json,Class <T> clazz)  throws Exception {
		
		try {
		
			ObjectMapper objectMapper =	new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			return	objectMapper.readValue(json, clazz);
	
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public List<T> converterStringParaListaObjeto(String jsonArray, Class<T[]> clazz)  throws Exception {
		try {
			
			ObjectMapper objectMapper =	new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			if(JsonUtil.isJSONValido(jsonArray)) {
				return Arrays.asList(objectMapper.readValue(jsonArray.getBytes("UTF-8"), clazz));
			} else {
				return new ArrayList<T>();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public static boolean isJSONValido(String jsonInString ) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			mapper.readTree(jsonInString);
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	

	
}
