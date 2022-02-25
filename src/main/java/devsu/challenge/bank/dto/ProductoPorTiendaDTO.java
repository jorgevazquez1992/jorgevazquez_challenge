package devsu.challenge.bank.dto;

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
public class ProductoPorTiendaDTO {

	private Long idProductoPorTienda;
	
	@NotNull(message = "Producto requerido")
	private Long productoIdProducto;
	
	@NotNull(message = "Tienda requerida")
	private Long tiendaIdTienda;
	
}
