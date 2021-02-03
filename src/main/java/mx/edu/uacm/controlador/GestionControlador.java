package mx.edu.uacm.controlador;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mx.edu.uacm.model.entitad.Dulce;
import mx.edu.uacm.model.servicio.ServicioCategoria;
import mx.edu.uacm.model.servicio.ServicioDulce;

@Controller
@RequestMapping(value = "/gestion_productos")
public class GestionControlador {
	@Autowired
	private ServicioDulce servicioDulce;
	@Autowired
	private ServicioCategoria servicioCategoria;
	
	@GetMapping(value = "/")
	public String listaClientes(Model model) {
		model.addAttribute("titulo", "Lista de Productos");
		model.addAttribute("dulces", servicioDulce.obtenerDulces());
		return "/gestion_productos/lista_productos";
	}
	
	@GetMapping(value = "/agregar_producto")
	public String agregar(Model model) {
		Dulce dulce = new Dulce();
		
		model.addAttribute("dulce", dulce);
		model.addAttribute("titulo", "Agregar Productos");
		model.addAttribute("categorias", servicioCategoria.obtenerCategorias());
		return "/gestion_productos/agregar_producto";
	}
	
	@GetMapping(value = "/editar_producto/{id}")
	public String modificar(Model model, 
			@PathVariable("id")Long id, RedirectAttributes attribute) {
		Dulce dulce = null;
		String ruta = "";
		if (id > 0) {
			dulce = servicioDulce.getDulce(id);
			if (dulce != null) {
				model.addAttribute("dulce", dulce);
				model.addAttribute("titulo", "Modificar Productos");
				model.addAttribute("categorias", servicioCategoria.obtenerCategorias());
				ruta = "/gestion_productos/agregar_producto";
			} else {
				ruta = "redirect:/gestion_productos/";
				attribute.addFlashAttribute("error", "Error: El ID del producto no exite :(");
			}
		} else {
			ruta = "redirect:/gestion_productos/";
			attribute.addFlashAttribute("error", "ATENCION: Error en el ID del producto :(");
		}
		
		return ruta;
	}
	
	@GetMapping(value = "/eliminar_producto/{id}")
	public String eliminar(Model model, 
			@PathVariable("id")Long id, RedirectAttributes attribute) {
		if (id <= 0) {
			attribute.addFlashAttribute("error", "ATENCION: Error en el ID del producto :(");
		} else  {
			servicioDulce.eliminarDulce(id);
		}
		return "redirect:/gestion_productos/";
	}
	
	@PostMapping(value = "/save")
	public String guardar(@Valid @ModelAttribute("dulce") Dulce dulce, 
			BindingResult result,
			@RequestParam(value = "file") MultipartFile imagen, 
			Model model, RedirectAttributes attribute) {
		String ruta = "";
		if (result.hasErrors()) {
			model.addAttribute("dulce", dulce);
			model.addAttribute("titulo", "Agregar Productos");
			model.addAttribute("categorias", servicioCategoria.obtenerCategorias());
			ruta = "/gestion_productos/agregar_producto";
		} else {
			almacenarImagen(imagen, dulce);
			if (dulce.getId() == null) {
				servicioDulce.crearDulce(dulce);
			} else {
				servicioDulce.actualizarDulce(dulce);
			}
			attribute.addFlashAttribute("success", "Producto almacenado con exito!");
			ruta = "redirect:/gestion_productos/";
		}
		
		return ruta;
	}
	
	/**
	 * Método que almecena la imagen en la carpeta /static/imagenes
	 * @param imagen
	 * @param dulce
	 */
	private void almacenarImagen(MultipartFile imagen, Dulce dulce) {
		if (!imagen.isEmpty()) {
			Path directorioImagenes = Paths.get("src/main/resources/static/imagenes");
			String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();
			byte[] bytesImg;
			try {
				bytesImg = imagen.getBytes();
				Path rutaCompleta = Paths.get(rutaAbsoluta + "/" + imagen.getOriginalFilename());
				Files.write(rutaCompleta, bytesImg);
				dulce.setImagen(imagen.getOriginalFilename());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	
}
