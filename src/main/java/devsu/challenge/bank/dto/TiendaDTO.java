package devsu.challenge.bank.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TiendaDTO {
	
	private Long idTienda;
	
	@NotNull(message = "Nombre de tienda requerido")
	private String nombre;
	
	List<ProductoPorTiendaDTO> productosPorTienda;

}
