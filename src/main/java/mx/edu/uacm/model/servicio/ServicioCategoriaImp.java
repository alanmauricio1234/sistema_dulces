package mx.edu.uacm.model.servicio;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import mx.edu.uacm.model.entitad.Categoria;

@Service
public class ServicioCategoriaImp implements ServicioCategoria {
	@Autowired
	private RestTemplate rest;
	private static final String URL_API = "https://warm-retreat-83627.herokuapp.com/api/dulces";

	@Override
	public List<Categoria> obtenerCategorias() {
		ResponseEntity<Categoria[]> response = rest.getForEntity(URL_API + "/categorias", Categoria[].class);
		return Arrays.asList(response.getBody());
	}

}
