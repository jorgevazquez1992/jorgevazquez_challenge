package testbuilders;

import java.util.List;

import devsu.challenge.bank.model.Producto;
import devsu.challenge.bank.model.ProductoPorTienda;
import lombok.Builder;

public class ProductoTestBuilder {

	@Builder
	public static Producto producto(Long idProducto, String codigo, String nombre, Float precio, Integer stock,
			List<ProductoPorTienda> productosPorTienda) {
		Producto producto = new Producto();
		producto.setIdProducto(idProducto);
		producto.setCodigo(codigo);
		producto.setNombre(nombre);
		producto.setPrecio(precio);
		producto.setStock(stock);
		if (productosPorTienda != null) {
			productosPorTienda.forEach(producto::addProductosPorTienda);
		}
		return producto;
	}

	public static class ProductoBuilder {
		private Long idProducto = 1L;
		private String codigo = "prod-1";
		private String nombre = "prod-name-1";
		private Float precio = 5.5f;
		private Integer stock = 10;
		private List<ProductoPorTienda> productosPorTienda = null;
	}

}
