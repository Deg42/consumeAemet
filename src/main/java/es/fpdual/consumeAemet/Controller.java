package es.fpdual.consumeAemet;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class Controller {

	
	@GetMapping("/ecija")
	public String getWeather() {
		String uri = "https://opendata.aemet.es/opendata/api/prediccion/especifica/municipio/diaria/41039";
		RestTemplate template = new RestTemplate();
		return template.getForObject(uri, String.class);
	}
	
	
}
