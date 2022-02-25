package testbuilders;

import devsu.challenge.bank.model.PedidoCabecera;
import devsu.challenge.bank.model.PedidoDetalle;
import devsu.challenge.bank.model.ProductoPorTienda;
import lombok.Builder;

public class PedidoDetalleTestBuilder {

	@Builder
	public static PedidoDetalle pedidoDetalle(Long idPedidoDetalle, PedidoCabecera pedidoCabecera, ProductoPorTienda productoPorTienda, Integer cantidad) {
		PedidoDetalle pedidoDetalle = new PedidoDetalle();
		pedidoDetalle.setIdPedidoDetalle(idPedidoDetalle);
		pedidoDetalle.setPedidoCabecera(pedidoCabecera);
		pedidoDetalle.setProductoPorTienda(productoPorTienda);
		pedidoDetalle.setCantidad(cantidad);
		return pedidoDetalle;
	}

	public static class PedidoDetalleBuilder{
		private Long idPedidoDetalle = 1L;
		private PedidoCabecera pedidoCabecera = PedidoCabeceraTestBuilder.builder().idPedidoCabecera(1L).build();
		private ProductoPorTienda productoPorTienda = ProductoPorTiendaTestBuilder.builder().idProductoPorTienda(1L).build();
		private Integer cantidad = 1;
	}
	
}
