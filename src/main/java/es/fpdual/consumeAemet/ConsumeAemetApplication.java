package es.fpdual.consumeAemet;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.google.gson.Gson;

@SpringBootApplication
public class ConsumeAemetApplication {

	public static void main(String[] args) throws IOException {
		ConfigurableApplicationContext ctx = SpringApplication.run(ConsumeAemetApplication.class, args);

		Gson jsonParser = new Gson();

		HttpClient httpclient = HttpClients.createDefault();

		HttpGet httpget = new HttpGet(
				"https://opendata.aemet.es/opendata/api/prediccion/especifica/municipio/diaria/41039?"+args[0]);

		HttpResponse response = httpclient.execute(httpget);

		HttpEntity entity = response.getEntity();

		String entityString = EntityUtils.toString(entity);

	
		
		RespuestaAemet json = jsonParser.fromJson(entityString, RespuestaAemet.class);
		
		HttpGet dataFile = new HttpGet(json.getDatos());
		
		HttpResponse dataRes = httpclient.execute(dataFile);
		
		HttpEntity dataEntity = dataRes.getEntity();
		
		String dataString = EntityUtils.toString(dataEntity);

		JSONArray result = new JSONArray(dataString);
		JSONObject jsonObject = (JSONObject) result.get(0);
		JSONObject prediccion = jsonObject.getJSONObject("prediccion");
		JSONObject dia = prediccion.getJSONArray("dia").getJSONObject(0);
		JSONObject temperatura = dia.getJSONObject("temperatura");
		
		
		String municipio = jsonObject.get("nombre").toString();
		Integer maxima =  Integer.valueOf(temperatura.get("maxima").toString());
		Integer minima =  Integer.valueOf(temperatura.get("minima").toString());
		
		System.out.println(municipio);
		System.out.println(maxima);
		System.out.println(minima);
		
		ctx.close();
	}
	
}
