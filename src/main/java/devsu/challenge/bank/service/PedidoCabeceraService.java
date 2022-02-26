package devsu.challenge.bank.service;

import java.util.List;

import devsu.challenge.bank.dto.MontoDTO;
import devsu.challenge.bank.dto.NumeroTransaccionesDTO;
import devsu.challenge.bank.dto.PedidoCabeceraDTO;
import devsu.challenge.bank.dto.PedidoDetalleDTO;
import devsu.challenge.bank.dto.TransaccionesClienteDTO;
import devsu.challenge.bank.model.PedidoDetalle;

public interface PedidoCabeceraService {

	PedidoCabeceraDTO guardarPedido(PedidoCabeceraDTO pedidoCabeceraDTO);

	List<PedidoDetalleDTO> transaccionesPorTienda(Long idTienda);

	List<NumeroTransaccionesDTO> transacciones();

	List<MontoDTO> montoPorProductoTienda();

	List<PedidoDetalle> buscarPorClienteRango(TransaccionesClienteDTO transaccionesClienteDTO);

}
