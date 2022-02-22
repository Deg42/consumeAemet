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
		String entityString;
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

	public JSONObject stringToJsonObject(String dataString) {
		JSONArray result = new JSONArray(dataString);
		return (JSONObject) result.get(0);
	}
		

	public String getMunicipio(JSONObject json) {
		return json.get("nombre").toString();
	};
	
	public JSONObject getPrediccionPorDia(JSONObject json, int dia) {
		return json.getJSONObject("prediccion").getJSONArray("dia").getJSONObject(dia);
	};
	
	public Integer getTemperaturaMaxima(JSONObject jsonDia) {
		JSONObject temperatura = jsonDia.getJSONObject("temperatura");
		
		return Integer.valueOf(temperatura.get("maxima").toString());
	};
	
	public Integer getTemperaturaMinima(JSONObject jsonDia) {
		JSONObject temperatura = jsonDia.getJSONObject("temperatura");
		
		return Integer.valueOf(temperatura.get("minima").toString());
	};
	
	//getDatos
	
	
}
