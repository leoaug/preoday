package gov.br.nasa.api.client;



import org.junit.Test;

import gov.nasa.api.bean.Temperature;
import gov.nasa.api.servico.cliente.ServicoClienteRest;
import gov.nasa.api.util.JsonUtil;

public class TemperaturaMarteClient {

	
	@Test
	public void test() {
		try {
			
			/**
			 ** Enpoint when SOL exists
			 * */
			//String url = "http://localhost:8080/preoday/nasa/temperature/416";
			
			/**
			 ** Enpoint when SOL dont exists
			 * */
			//String url = "http://localhost:8080/preoday/nasa/temperature/41645656";
			
			/**
			 ** Enpoint for all sol_keys in week
			 * */
			String url = "http://localhost:8080/preoday/nasa/temperature";
			
			String resposta = ServicoClienteRest.GET(url, true, null, null,
					   5000);
			if(resposta.contains("erro Causa:")) {
				System.out.println("SOL inexistente, informe outro, causa: " + resposta);
			} else {
				Temperature temperature = new JsonUtil<Temperature>().converterStringParaObjeto(resposta, Temperature.class) ;
				
				System.out.println("Average = " +temperature.getAverageTemperature());
			}
			
		} catch (Exception e) {
			e.printStackTrace(); 
		}	
	}
	
}
