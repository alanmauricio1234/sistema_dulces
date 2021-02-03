package mx.edu.uacm.model.entitad;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor @Builder
public class Categoria {
	
	@Setter(value = AccessLevel.NONE)
	private Long id;
	private String nombre;
	
	public void setId(Long id) {
		if (id != null && this.id != id) {
			this.id = id;
		}
	}
	
	
}
