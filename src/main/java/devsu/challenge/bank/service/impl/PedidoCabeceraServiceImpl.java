package devsu.challenge.bank.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import devsu.challenge.bank.dto.PedidoCabeceraDTO;
import devsu.challenge.bank.dto.PedidoDetalleDTO;
import devsu.challenge.bank.exception.ResourceNotFoundException;
import devsu.challenge.bank.exception.UnprocessableEntityException;
import devsu.challenge.bank.model.Cliente;
import devsu.challenge.bank.model.GenericResponse;
import devsu.challenge.bank.model.PedidoCabecera;
import devsu.challenge.bank.model.PedidoDetalle;
import devsu.challenge.bank.model.Producto;
import devsu.challenge.bank.model.ProductoPorTienda;
import devsu.challenge.bank.repository.ClienteRepository;
import devsu.challenge.bank.repository.PedidoCabeceraRepository;
import devsu.challenge.bank.repository.PedidoDetalleRepository;
import devsu.challenge.bank.repository.ProductoPorTiendaRepository;
import devsu.challenge.bank.repository.ProductoRepository;
import devsu.challenge.bank.service.PedidoCabeceraService;

@Service
public class PedidoCabeceraServiceImpl implements PedidoCabeceraService {

	private final ProductoPorTiendaRepository productoPorTiendaRepository;
	private final PedidoCabeceraRepository pedidoCabeceraRepository;
	private final PedidoDetalleRepository pedidoDetalleRepository;
	private final ProductoRepository productoRepository;
	private final ClienteRepository clienteRepository;
	private final ModelMapper modelMapper;

	@Autowired
	public PedidoCabeceraServiceImpl(@Autowired PedidoCabeceraRepository pedidoCabeceraRepository,
			PedidoDetalleRepository pedidoDetalleRepository, ClienteRepository clienteRepository,
			ProductoRepository productoRepository, ProductoPorTiendaRepository productoPorTiendaRepository,
			ModelMapper modelMapper) {
		this.productoPorTiendaRepository = productoPorTiendaRepository;
		this.pedidoCabeceraRepository = pedidoCabeceraRepository;
		this.pedidoDetalleRepository = pedidoDetalleRepository;
		this.productoRepository = productoRepository;
		this.clienteRepository = clienteRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	@Transactional
	public PedidoCabeceraDTO guardarPedido(PedidoCabeceraDTO pedidoCabeceraDTO) {
		Cliente cliente = buscarClientePorId(pedidoCabeceraDTO.getIdCliente());
		PedidoCabecera pedidoCabecera = new PedidoCabecera(cliente);
		pedidoCabecera.setFechaPedido(pedidoCabeceraDTO.getFechaPedido());
		pedidoCabecera = pedidoCabeceraRepository.save(pedidoCabecera);
		for (PedidoDetalleDTO pedidoDetalleDTO : pedidoCabeceraDTO.getDetalles()) {
			ProductoPorTienda productoPorTienda = buscarProductoPorTiendaPorId(
					pedidoDetalleDTO.getProductoPorTiendaIdProductoPorTienda());
			validarStock(productoPorTienda.getProducto(), pedidoDetalleDTO.getCantidad());
			pedidoDetalleDTO.setPedidoCabeceraIdPedidoCabecera(pedidoCabecera.getIdPedidoCabecera());
			PedidoDetalle pedidoDetalle = modelMapper.map(pedidoDetalleDTO, PedidoDetalle.class);
			pedidoDetalle = pedidoDetalleRepository.save(pedidoDetalle);
			pedidoCabecera.addDetalles(pedidoDetalle);
			productoPorTienda.getProducto()
					.setStock(productoPorTienda.getProducto().getStock() - pedidoDetalleDTO.getCantidad());
			productoRepository.save(productoPorTienda.getProducto());
		}
		return modelMapper.map(pedidoCabecera, PedidoCabeceraDTO.class);
	}

	private Cliente buscarClientePorId(Long clienteId) {
		return clienteRepository.findById(clienteId)
				.orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
	}

	private ProductoPorTienda buscarProductoPorTiendaPorId(Long idProductoPorTienda) {
		return productoPorTiendaRepository.findById(idProductoPorTienda)
				.orElseThrow(() -> new ResourceNotFoundException("Producto por tienda no encontrado"));
	}

	@Transactional
	private void validarStock(Producto producto, Integer compra) {
		if (producto.getStock() < compra) {
			Integer pendiente = compra - producto.getStock();
			if (pendiente > 10) {
				throw new UnprocessableEntityException("Unidades no disponibles (> 10)");
			} else if (pendiente > 5 && pendiente <= 10) {
				// LLAMADA A SERVICIO
				RestTemplate restTemplate = new RestTemplate();
				ResponseEntity<GenericResponse> response = restTemplate.getForEntity(
						"https://mocki.io/v1/0a36b837-dd6c-4757-a4d1-148074adae40", GenericResponse.class);
				producto.setStock(producto.getStock() + response.getBody().getStock());
				producto = productoRepository.save(producto);
				// LLAMADA A SERVICIO
			} else if (pendiente <= 5) {
				// LLAMADA A SERVICIO ASINCRONO
				actualizarStockAsincrono(producto);
				// LLAMADA A SERVICIO ASINCRONO
			}
		}
	}

	@Async
	private void actualizarStockAsincrono(Producto producto) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<GenericResponse> response = restTemplate.getForEntity(
				"https://mocki.io/v1/7efa94f9-4bef-4213-972b-1338177edf9a", GenericResponse.class);
		producto.setStock(producto.getStock() + response.getBody().getStock());
		producto = productoRepository.save(producto);
	}
	
}
