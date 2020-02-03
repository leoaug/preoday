package gov.nasa.api.resource;


import javax.servlet.http.HttpServletRequest;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gov.nasa.api.bean.JSO;
import gov.nasa.api.bean.Temperature;
import gov.nasa.api.constantes.EndPoint;
import gov.nasa.api.servico.cliente.ServicoClienteRest;
import gov.nasa.api.util.JsonUtil;


@RestController
@RequestMapping(value = "/nasa")
public class TemperaturaMarteResource {

	
	
	@GetMapping(value = "/temperature")
	public  ResponseEntity <?> temperature(HttpServletRequest request) throws Exception {
		try {
			
			
			JSONObject jsonObject = new JSONObject(ServicoClienteRest.GET(EndPoint.INSIGHT_WEATHER, 
					   true,
					   null,
					   null,
					   5000));
			
			/**
			 *
			 *PEgando os keys dos Sol's
			 */
			JSONArray JSONArraySolKeys = new JSONArray(jsonObject.get("sol_keys").toString()) ;
			double totalTemperatures = 0;
			for (int i = 0; i < JSONArraySolKeys.length(); i++) {
				
				JSO JSO = new JsonUtil<JSO>() .
						converterStringParaObjeto(jsonObject.get(JSONArraySolKeys.getString(i)).toString(), JSO.class) ;
				totalTemperatures += JSO.getAt().getAv();
								
			}
			
			Temperature temperature = new Temperature();
			temperature.setAverageTemperature(totalTemperatures / JSONArraySolKeys.length());
			
			
			return new ResponseEntity <Temperature> (temperature,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity <String>("{\"erro\":\""+e.getMessage()+"\"}",HttpStatus.INTERNAL_SERVER_ERROR);

			
		}
	}
	@GetMapping(value = "/temperature/{SOL}")
	public  ResponseEntity <?> temperature(@PathVariable("SOL") String SOL,HttpServletRequest request) throws Exception {
		try {
			
			
			JSONObject jsonObject = new JSONObject(ServicoClienteRest.GET(EndPoint.INSIGHT_WEATHER, 
					   true,
					   null,
					   null,
					   5000));
			
			
			if(jsonObject.isNull(SOL)) {
				return new ResponseEntity <String>("{\"erro\":\"SOL não enocontrado\"}",HttpStatus.INTERNAL_SERVER_ERROR);
			} else {
				JSO jso = new JsonUtil<JSO>() .converterStringParaObjeto(jsonObject.get(SOL).toString(), JSO.class) ;
				
				
				Temperature temperature = new Temperature();
				temperature.setAverageTemperature(jso.getAt().getAv());
				
				return new ResponseEntity <Temperature>  (temperature,HttpStatus.OK);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity <String>("{\"erro\":\""+e.getMessage()+"\"}",HttpStatus.INTERNAL_SERVER_ERROR);

			
		}
	}
	
	

}
