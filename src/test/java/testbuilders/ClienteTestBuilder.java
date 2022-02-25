package testbuilders;

import devsu.challenge.bank.model.Cliente;
import lombok.Builder;

public class ClienteTestBuilder {

	@Builder
	public static Cliente cliente(Long idCliente, String identificacion, String nombre, String urlFoto) {
		Cliente cliente = new Cliente();
		cliente.setIdCliente(idCliente);
		cliente.setIdentificacion(identificacion);
		cliente.setNombre(nombre);
		cliente.setUrlFoto(urlFoto);
		return cliente;
	}

	public static class ClienteBuilder {
		private String identificacion = "0303030303";
		private String nombre = "NOMBRE GENERICO";
		private String urlFoto = "www.google.com";
	}

}
