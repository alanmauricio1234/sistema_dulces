package mx.edu.uacm.model.servicio;

import java.util.List;

import mx.edu.uacm.model.entitad.Dulce;

public interface ServicioDulce {
	public List<Dulce> obtenerDulces();
	public Dulce getDulce(Long id);
	public Dulce crearDulce(Dulce dulce);
	public void actualizarDulce(Dulce dulce);
	public void eliminarDulce(Long id);
	public void actualzarStock(Long id, Integer cantidad);
}
