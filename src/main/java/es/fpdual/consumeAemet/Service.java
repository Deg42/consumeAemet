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

@org.springframework.stereotype.Service
public class Service {
	
	String url = "https://opendata.aemet.es/opendata/api/prediccion/especifica/municipio/diaria/";
	
	// This would be and env param
	private static final String APIKEY = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqZ29uemFsZXpyaXZlcmFAZWNpLnNhZmFuZXQuZXMiLCJqdGkiOiI1ZjVjYjBhYy1hM2NjLTQyYjAtOGU3Yy02YjlhZGM3NDVhZjIiLCJpc3MiOiJBRU1FVCIsImlhdCI6MTY0NTEwNDk4MCwidXNlcklkIjoiNWY1Y2IwYWMtYTNjYy00MmIwLThlN2MtNmI5YWRjNzQ1YWYyIiwicm9sZSI6IiJ9.Di_e909jMRgGn4ZOc0zYF1-iQJKEQMWGQMXBiJB0T9M";

	Gson jsonParser = new Gson();

	HttpClient httpclient = HttpClients.createDefault();
	
	public static String getApikey() {
		return APIKEY;
	}

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
	
	public DatosAemet getDatos(String idMunicipio) {
				
		HttpEntity entidad = this.getAemetEntity(url + idMunicipio, APIKEY);
		RespuestaAemet respuesta = this.entityToClass(entidad);
		String data = aemetEntityToString(respuesta);
		JSONObject json = stringToJsonObject(data);
		
		DatosAemet datos = new DatosAemet();
		
		datos.setMunicipio(this.getMunicipio(json));
		datos.setTemperaturaMaxima(this.getTemperaturaMaxima(this.getPrediccionPorDia(json, 0)));
		datos.setTemperaturaMinima(this.getTemperaturaMinima(this.getPrediccionPorDia(json, 0)));
		
		return datos;
	}
		
}
