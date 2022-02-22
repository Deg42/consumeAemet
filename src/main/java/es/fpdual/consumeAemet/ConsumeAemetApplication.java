package es.fpdual.consumeAemet;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ConsumeAemetApplication {

	static String url = "https://opendata.aemet.es/opendata/api/prediccion/especifica/municipio/diaria/";
	static String idMunicipio = "41039";

	public static void main(String[] args) throws IOException {
		ConfigurableApplicationContext ctx = SpringApplication.run(ConsumeAemetApplication.class, args);

		Controller controller = new Controller();

		HttpEntity entidad = controller.getAemetEntity(url, args[0]);
		
		RespuestaAemet respuesta = controller.entityToClass(entidad);

		ctx.close();
	}

}
