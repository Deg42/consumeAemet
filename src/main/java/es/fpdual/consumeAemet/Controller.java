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

	String apiKey;

	Gson jsonParser = new Gson();

	HttpClient httpclient = HttpClients.createDefault();

	public HttpEntity getAemetEntity(String url, String apiKey) {
		HttpGet httpget = new HttpGet(url);
		httpget.addHeader("api_key", apiKey);

		HttpResponse response;
		HttpEntity entity;
		try {
			response = httpclient.execute(httpget);
			entity = response.getEntity();
			return entity;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;	
	}

	public RespuestaAemet entityToClass(HttpEntity entity) {
		String entityString = "";
		try {
			entityString = EntityUtils.toString(entity);
			return jsonParser.fromJson(entityString, RespuestaAemet.class);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String aemetEntityToString(RespuestaAemet json) {
		HttpGet dataFile = new HttpGet(json.getDatos());
		HttpResponse dataRes;
		try {
			dataRes = httpclient.execute(dataFile);
			HttpEntity dataEntity = dataRes.getEntity();
			return EntityUtils.toString(dataEntity);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public void resultSet(String dataString) {
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
		System.out.println(minima);
	}

}
