package es.fpdual.consumeAemet;

public class RespuestaAemet {

	
	String descripcion;
	int estado;
	String datos;
	String metadatos;
	
	public String getDescripcion() {
		return descripcion;
	}
	public int getEstado() {
		return estado;
	}
	public String getDatos() {
		return datos;
	}
	public String getMetadatos() {
		return metadatos;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public void setDatos(String datos) {
		this.datos = datos;
	}
	public void setMetadatos(String metadatos) {
		this.metadatos = metadatos;
	}
	
	
}