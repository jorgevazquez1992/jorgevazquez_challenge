package devsu.challenge.bank.dto;

import java.util.Date;

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
public class NumeroTransaccionesDTO {
	
	private Long idTienda;
	private String nombreTienda;
	private Date fecha;
	private Long contador;
		
}
