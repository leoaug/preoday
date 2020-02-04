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
			 ** Enpoint para quadno quiser o SOL
			 * */
			String url = "http://localhost:8080/preoday/nasa/temperature/416";
			
			/**
			 ** Enpoint para quando quiser ql SOL
			 * */
			//String url = "http://localhost:8080/preoday/nasa/temperature";
			
			Temperature temperature = new JsonUtil<Temperature>().converterStringParaObjeto(ServicoClienteRest.GET(url, 
					   true,
					   null,
					   null,
					   5000), Temperature.class) ;
			
			System.out.println("Average = " +temperature.getAverageTemperature());
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
}
