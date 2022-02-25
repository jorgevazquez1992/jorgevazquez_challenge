package devsu.challenge.bank.dto;

import java.util.Date;
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
public class PedidoCabeceraDTO {

	private Long idPedidoCabecera;
	
	@NotNull(message = "Cliente requerido")
	private Long idCliente;
	
	@NotNull(message = "Fecha requerida")
	private Date fechaPedido;
	
	List<PedidoDetalleDTO> detalles;	
	
}
