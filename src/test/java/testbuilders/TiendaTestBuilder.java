package testbuilders;

import devsu.challenge.bank.model.Tienda;
import lombok.Builder;

public class TiendaTestBuilder {

	@Builder
	public static Tienda tienda(Long idTienda,String nombre) {
		Tienda tienda = new Tienda(nombre);
		tienda.setIdTienda(idTienda);
		return tienda;
	}
	
	public static class TiendaBuilder{
		private Long idTienda = 1L;
		private String tienda = "NOMBRE TIENDA";
	}
	
}
