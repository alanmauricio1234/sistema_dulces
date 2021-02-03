package mx.edu.uacm.model.entitad;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Getter @Setter
@AllArgsConstructor  @Builder
public class Dulce {
	
	public Dulce() {
		this.categoria = new Categoria();
	}
	
	private Long id;
	@NotEmpty(message = "El nombre es un campo requerido")
	private String nombre;
	
	@NotEmpty(message = "La marca es un campo requerido")
	private String marca;
	
	@NotNull(message = "La cantidad tiene que ser un número")
	@Min(value= 1, message = "El valor mínimo del stock es 1")
	private Integer cantidad;
	
	@NotEmpty(message = "La descripción es requerida")
	private String descripcion;
	
	private String imagen;
	
	@NotNull(message = "El descuento es un campo requerido")
	@DecimalMax(value = "70", message = "El valor maximo es 70")
	@DecimalMin(value = "0", message = "El valor mínimo es 0")
	private BigDecimal descuento;
	
	@NotNull(message = "El precio es un campo requerido requerido")
	@DecimalMin(value = "1", message = "El valor minimo del precio es 1")
	private BigDecimal precio;
	
	private Categoria categoria;
	
	
	
}
