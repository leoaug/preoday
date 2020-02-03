package gov.br.nasa.api.client;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import gov.nasa.api.constantes.EndPoint;
import gov.nasa.api.servico.cliente.ServicoClienteRest;

public class TemperaturaMarteClient {

	
	@Test
	public void test() {
		System.out.println("yeahh");
		try {
			
			
			//Map <String, String> params = new HashMap <String, String>();
			//params.put(key, value)
			
			String url = "http://localhost:8080/preoday/nasa/temperature/415";
			
			String resultado = ServicoClienteRest.GET(url, 
								   true,
								   null,
								   null,
								   5000);
			
			System.out.println(resultado);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
}
