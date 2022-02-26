package devsu.challenge.bank.dto;

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
public class MontoDTO {

	private Long idProducto;
	private String nombreProducto;
	private Long idTienda;
	private String nombreTienda;
	private Float monto;
}
