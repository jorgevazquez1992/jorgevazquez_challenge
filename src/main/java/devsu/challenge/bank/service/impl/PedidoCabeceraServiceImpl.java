package devsu.challenge.bank.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import devsu.challenge.bank.dto.MontoDTO;
import devsu.challenge.bank.dto.NumeroTransaccionesDTO;
import devsu.challenge.bank.dto.PedidoCabeceraDTO;
import devsu.challenge.bank.dto.PedidoDetalleDTO;
import devsu.challenge.bank.dto.TransaccionesClienteDTO;
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

	@Override
	@Transactional(readOnly = true)
	public List<PedidoDetalleDTO> transaccionesPorTienda(Long idTienda) {
		List<PedidoDetalle> detalles = pedidoDetalleRepository.findByTienda(idTienda).orElse(null);
		return detalles.stream().map(detalle -> modelMapper.map(detalle, PedidoDetalleDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<NumeroTransaccionesDTO> transacciones() {
		return pedidoDetalleRepository.contarPorTiendaFecha();
	}

	@Override
	@Transactional(readOnly = true)
	public List<MontoDTO> montoPorProductoTienda() {
		List<MontoDTO> respuesta = new ArrayList<MontoDTO>();
		List<PedidoDetalle> detalles = pedidoDetalleRepository.findAll();
		if (!detalles.isEmpty()) {
			List<ProductoPorTienda> productosPorTienda = detalles.stream()
					.map(detalle -> detalle.getProductoPorTienda()).distinct().collect(Collectors.toList());
			for (ProductoPorTienda productoPorTienda : productosPorTienda) {
				MontoDTO mDto = new MontoDTO();
				mDto.setIdProducto(productoPorTienda.getProducto().getIdProducto());
				mDto.setNombreProducto(productoPorTienda.getProducto().getNombre());
				mDto.setIdTienda(productoPorTienda.getTienda().getIdTienda());
				mDto.setNombreTienda(productoPorTienda.getTienda().getNombre());
				Float monto = detalles.stream()
						.filter(detalle -> detalle.getProductoPorTienda().getIdProductoPorTienda() == productoPorTienda
								.getIdProductoPorTienda())
						.map(detalle -> detalle.getCantidad()
								* detalle.getProductoPorTienda().getProducto().getPrecio())
						.reduce(0f, Float::sum);
				mDto.setMonto(monto);
				respuesta.add(mDto);
			}
		}
		return respuesta;
	}

	@Override
	@Transactional(readOnly = true)
	public List<PedidoDetalle> buscarPorClienteRango(TransaccionesClienteDTO transaccionesClienteDTO) {
		buscarClientePorId(transaccionesClienteDTO.getIdCliente());
		if (transaccionesClienteDTO.getFechaInicio().after(transaccionesClienteDTO.getFechaFin())) {
			throw new UnprocessableEntityException("Rango de fechas invalido");
		}
		List<PedidoDetalle> resultado = pedidoDetalleRepository.buscarPorClienteRango(transaccionesClienteDTO.getIdCliente(), transaccionesClienteDTO.getFechaInicio(),transaccionesClienteDTO.getFechaFin()).orElse(null);
		return resultado;
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
		ResponseEntity<GenericResponse> response = restTemplate
				.getForEntity("https://mocki.io/v1/7efa94f9-4bef-4213-972b-1338177edf9a", GenericResponse.class);
		producto.setStock(producto.getStock() + response.getBody().getStock());
		producto = productoRepository.save(producto);
	}

}
