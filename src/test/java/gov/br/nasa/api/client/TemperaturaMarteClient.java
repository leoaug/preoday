package gov.br.nasa.api.client;



import org.junit.Test;
import gov.nasa.api.servico.cliente.ServicoClienteRest;

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
