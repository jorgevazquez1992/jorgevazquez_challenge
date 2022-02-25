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
public class ProductoDTO {

	private Long idProducto;
	
	@NotNull(message = "Codigo requerido")
	private String codigo;
	
	@NotNull(message = "Nombre requerido")
	private String nombre;
	
	@NotNull(message = "Precio requerido")
	private Float precio;
	
	@NotNull(message = "Stock requerido")
	private Integer stock;
	
	List<ProductoPorTiendaDTO> productosPorTienda;
	
}
