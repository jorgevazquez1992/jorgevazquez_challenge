package testbuilders;

import devsu.challenge.bank.model.Producto;
import devsu.challenge.bank.model.ProductoPorTienda;
import devsu.challenge.bank.model.Tienda;
import lombok.Builder;

public class ProductoPorTiendaTestBuilder {

	@Builder
	public static ProductoPorTienda productoPorTienda(Long idProductoPorTienda, Producto producto, Tienda tienda) {
		ProductoPorTienda productoPorTienda = new ProductoPorTienda();
		productoPorTienda.setIdProductoPorTienda(idProductoPorTienda);
		productoPorTienda.setProducto(producto);
		productoPorTienda.setTienda(tienda);
		return productoPorTienda;
	}
	
	public static class ProductoPorTiendaBuilder {
		private Long idProductoPorTienda = 1L;
		private Producto producto = ProductoTestBuilder.builder().idProducto(1L).build();
		private Tienda tienda = TiendaTestBuilder.builder().idTienda(1L).build();
	}
	
}
