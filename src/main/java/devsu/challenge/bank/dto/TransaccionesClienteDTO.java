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
public class TransaccionesClienteDTO {

	private Long idCliente;
	private Date fechaInicio;
	private Date fechaFin;
	
}
