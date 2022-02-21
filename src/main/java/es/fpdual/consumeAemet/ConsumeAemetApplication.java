package es.fpdual.consumeAemet;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ConsumeAemetApplication {
	
	static String idMunicipio = "41039";

	public static void main(String[] args) throws IOException {
		ConfigurableApplicationContext ctx = SpringApplication.run(ConsumeAemetApplication.class, args);

		Controller controller = new Controller(args[0]);
		
		RespuestaAemet respuesta = controller.jsonAemet(
				"https://opendata.aemet.es/opendata/api/prediccion/especifica/municipio/diaria/" + idMunicipio);

		String resultString = controller.stringAemet(respuesta);

		controller.resultSet(resultString);

		ctx.close();
	}

}
