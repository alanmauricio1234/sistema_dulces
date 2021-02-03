package mx.edu.uacm.model.servicio;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import mx.edu.uacm.model.entitad.Dulce;

@Service
public class ServicioDulceImp implements ServicioDulce {
	@Autowired
	private RestTemplate rest;
	private static final String URL_API = "https://warm-retreat-83627.herokuapp.com/api/dulces";
	
	/**
	 * Método que obtiene los dulces de la API
	 */
	@Override
	public List<Dulce> obtenerDulces() {
		ResponseEntity<Dulce[]> response = rest.getForEntity(URL_API, Dulce[].class);
		
		return Arrays.asList(response.getBody());
	}
	
	
	/**
	 * Método que obtiene un dulce 
	 */
	@Override
	public Dulce getDulce(Long id) {
		ResponseEntity<Dulce> response = rest.getForEntity(URL_API + "/{id}", Dulce.class, id);
		return response.getBody();
	}
	
	/**
	 * Método que crea un dulce y lo almacena
	 */
	@Override
	public Dulce crearDulce(Dulce dulce) {
		ResponseEntity<Dulce> response = rest.postForEntity(URL_API, dulce, Dulce.class);
		return response.getBody();
	}

	@Override
	public void actualizarDulce(Dulce dulce) {
		rest.put(URL_API + "/{id}", dulce, dulce.getId());
		
	}

	@Override
	public void eliminarDulce(Long id) {
		rest.delete(URL_API + "/{id}", id);
	}

	@Override
	public void actualzarStock(Long id, Integer cantidad) {
		
	}

}
