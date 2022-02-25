package testbuilders;

import java.util.Date;
import java.util.List;

import devsu.challenge.bank.model.Cliente;
import devsu.challenge.bank.model.PedidoCabecera;
import devsu.challenge.bank.model.PedidoDetalle;
import lombok.Builder;

public class PedidoCabeceraTestBuilder {

	@Builder
	public static PedidoCabecera pedidoCabecera(Long idPedidoCabecera, Cliente cliente, Date fechaPedido,
			List<PedidoDetalle> detalles) {
		PedidoCabecera pedidoCabecera = new PedidoCabecera(cliente);
		pedidoCabecera.setIdPedidoCabecera(idPedidoCabecera);
		pedidoCabecera.setFechaPedido(fechaPedido);
		if (detalles != null) {
			detalles.forEach(pedidoCabecera::addDetalles);
		}
		return pedidoCabecera;
	}

	public static class PedidoCabeceraBuilder {
		private Long idPedidoCabecera = 1L;
		private Cliente cliente = ClienteTestBuilder.builder().idCliente(1L).build();
		private Date fechaPedido = new Date();
		private List<PedidoDetalle> detalles = null;

	}

}
