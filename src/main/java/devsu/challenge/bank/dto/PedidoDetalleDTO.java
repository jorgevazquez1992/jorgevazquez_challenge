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
public class PedidoDetalleDTO {

	private Long idPedidoDetalle;
	
	@NotNull(message = "Cabecera requerida")
	private Long pedidoCabeceraIdPedidoCabecera;
	
	@NotNull(message = "Producto por tienda requerida")
	private Long productoPorTiendaIdProductoPorTienda;
	
	@NotNull(message = "Cantidad requerida")
	private Integer cantidad;
	
}
