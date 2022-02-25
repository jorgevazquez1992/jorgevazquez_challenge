package devsu.challenge.bank.service;

import java.util.List;

import devsu.challenge.bank.dto.PedidoCabeceraDTO;
import devsu.challenge.bank.dto.PedidoDetalleDTO;

public interface PedidoCabeceraService {

	PedidoCabeceraDTO guardarPedido(PedidoCabeceraDTO pedidoCabeceraDTO);

	List<PedidoDetalleDTO> transaccionesPorTienda(Long idTienda);

}
