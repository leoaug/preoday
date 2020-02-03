package gov.br.nasa.api.client;



import org.junit.Test;
import gov.nasa.api.servico.cliente.ServicoClienteRest;

public class TemperaturaMarteClient {

	
	@Test
	public void test() {
		System.out.println("yeahh");
		try {
			
			
			
			String url = "http://localhost:8080/preoday/nasa/temperature/415";
			
			//String url = "http://localhost:8080/preoday/nasa/temperature";
			
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
