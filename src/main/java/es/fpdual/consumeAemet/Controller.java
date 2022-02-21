package es.fpdual.consumeAemet;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;

public class Controller {
	Gson jsonParser = new Gson();

	HttpClient httpclient = HttpClients.createDefault();
	
	String apiKey;
	
	public Controller(String apiKey) {
		super();
		this.apiKey = apiKey;
	}

	public RespuestaAemet jsonAemet(String url) throws ClientProtocolException, IOException {
		HttpGet httpget = new HttpGet(
				url+ "?" + apiKey);

			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			String entityString = EntityUtils.toString(entity);

			return jsonParser.fromJson(entityString, RespuestaAemet.class);
	}

	public String stringAemet(RespuestaAemet json) throws ParseException, IOException {
		HttpGet dataFile = new HttpGet(json.getDatos());
		HttpResponse dataRes = httpclient.execute(dataFile);
		HttpEntity dataEntity = dataRes.getEntity();

		return EntityUtils.toString(dataEntity);
	}
	
	public void resultSet(String dataString){
	JSONArray result = new JSONArray(dataString);
	JSONObject jsonObject = (JSONObject) result.get(0);
	JSONObject prediccion = jsonObject.getJSONObject("prediccion");
	JSONObject dia = prediccion.getJSONArray("dia").getJSONObject(0);
	JSONObject temperatura = dia.getJSONObject("temperatura");

	String municipio = jsonObject.get("nombre").toString();
	Integer maxima = Integer.valueOf(temperatura.get("maxima").toString());
	Integer minima = Integer.valueOf(temperatura.get("minima").toString());
	
	System.out.println(municipio);
	System.out.println(maxima);
	System.out.println(minima);;
	
	}
	
}
