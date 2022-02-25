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
public class ClienteDTO {

	private Long idCliente;
	
	@NotNull(message = "Identificacion no puede ser null")
	private String identificacion;
	
	@NotNull(message = "Nombre no puede ser null")
	private String nombre;
	
	@NotNull(message = "URL de la foto no puede ser null")
	private String urlFoto;
	
}
