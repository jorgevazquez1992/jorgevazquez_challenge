package devsu.challenge.bank.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import devsu.challenge.bank.dto.PedidoCabeceraDTO;
import devsu.challenge.bank.dto.PedidoDetalleDTO;
import devsu.challenge.bank.exception.ResourceNotFoundException;
import devsu.challenge.bank.exception.UnprocessableEntityException;
import devsu.challenge.bank.model.Cliente;
import devsu.challenge.bank.model.PedidoCabecera;
import devsu.challenge.bank.model.PedidoDetalle;
import devsu.challenge.bank.model.Producto;
import devsu.challenge.bank.model.ProductoPorTienda;
import devsu.challenge.bank.model.Tienda;
import devsu.challenge.bank.repository.ClienteRepository;
import devsu.challenge.bank.repository.PedidoCabeceraRepository;
import devsu.challenge.bank.repository.PedidoDetalleRepository;
import devsu.challenge.bank.repository.ProductoPorTiendaRepository;
import devsu.challenge.bank.repository.ProductoRepository;
import devsu.challenge.bank.repository.TiendaRepository;
import devsu.challenge.bank.service.impl.PedidoCabeceraServiceImpl;
import testbuilders.ClienteTestBuilder;
import testbuilders.PedidoCabeceraTestBuilder;
import testbuilders.PedidoDetalleTestBuilder;
import testbuilders.ProductoPorTiendaTestBuilder;
import testbuilders.ProductoTestBuilder;
import testbuilders.TiendaTestBuilder;

@ExtendWith(MockitoExtension.class)
public class PedidoCabeceraServiceTest {

	@InjectMocks
	private PedidoCabeceraServiceImpl pedidoCabeceraService;
	@Mock
	private PedidoCabeceraRepository pedidoCabeceraRepository;
	@Mock
	private ClienteRepository clienteRepository;
	@Mock
	private ProductoRepository productoRepository;
	@Mock
	private PedidoDetalleRepository pedidoDetalleRepository;
	@Mock
	private ProductoPorTiendaRepository productoPorTiendaRepository;
	@Mock
	private TiendaRepository tiendaRepository;
	@Mock
	private ModelMapper modelMapper;

	@Test
	void crearPedidoExitoso() {
		Cliente cliente = ClienteTestBuilder.builder().idCliente(1L).identificacion("0000000000")
				.nombre("NOMBRE CLIENTE").urlFoto("URL FOTO CLIENTE").build();
		lenient().when(clienteRepository.findById(cliente.getIdCliente())).thenReturn(Optional.of(cliente));
		Producto producto = ProductoTestBuilder.builder().idProducto(1L).codigo("prod-1").nombre("prod-name-1")
				.precio(5.5f).stock(10).build();
		lenient().when(productoRepository.findById(producto.getIdProducto())).thenReturn(Optional.of(producto));
		Tienda tienda = TiendaTestBuilder.builder().idTienda(1L).nombre("Tienda 1").build();
		lenient().when(tiendaRepository.findById(tienda.getIdTienda())).thenReturn(Optional.of(tienda));
		ProductoPorTienda productoPorTienda = ProductoPorTiendaTestBuilder.builder().idProductoPorTienda(1L)
				.producto(producto).tienda(tienda).build();
		lenient().when(productoPorTiendaRepository.findById(productoPorTienda.getIdProductoPorTienda()))
				.thenReturn(Optional.of(productoPorTienda));
		PedidoDetalle pedidoDetalle = PedidoDetalleTestBuilder.builder().productoPorTienda(productoPorTienda)
				.cantidad(1).build();
		PedidoDetalleDTO pedidoDetalleDTO = PedidoDetalleDTO.builder()
				.productoPorTiendaIdProductoPorTienda(pedidoDetalle.getProductoPorTienda().getIdProductoPorTienda())
				.cantidad(pedidoDetalle.getCantidad()).build();
		List<PedidoDetalle> detalles = Lists.newArrayList(pedidoDetalle);
		List<PedidoDetalleDTO> detallesDTO = Lists.newArrayList(pedidoDetalleDTO);
		PedidoCabecera pedidoCabecera = PedidoCabeceraTestBuilder.builder().cliente(cliente).fechaPedido(new Date())
				.detalles(detalles).build();
		PedidoCabeceraDTO pedidoCabeceraDTO = PedidoCabeceraDTO.builder()
				.idCliente(pedidoCabecera.getCliente().getIdCliente()).fechaPedido(pedidoCabecera.getFechaPedido())
				.detalles(detallesDTO).build();
		lenient().when(pedidoCabeceraRepository.save(Mockito.any(PedidoCabecera.class))).thenReturn(pedidoCabecera);
		pedidoCabeceraService.guardarPedido(pedidoCabeceraDTO);
		ArgumentCaptor<PedidoCabecera> pedidoCaptor = ArgumentCaptor.forClass(PedidoCabecera.class);
		Mockito.verify(pedidoCabeceraRepository).save(pedidoCaptor.capture());
		Mockito.verify(modelMapper).map(pedidoCabecera, PedidoCabeceraDTO.class);
		assertEquals(pedidoCabecera.getFechaPedido(), pedidoCaptor.getValue().getFechaPedido());
	}
	
	@Test
	void PedidoRelacionNoExistente() {
		Cliente cliente = ClienteTestBuilder.builder().idCliente(1L).identificacion("0000000000")
				.nombre("NOMBRE CLIENTE").urlFoto("URL FOTO CLIENTE").build();
		lenient().when(clienteRepository.findById(cliente.getIdCliente())).thenReturn(Optional.of(cliente));
		Producto producto = ProductoTestBuilder.builder().idProducto(1L).codigo("prod-1").nombre("prod-name-1")
				.precio(5.5f).stock(10).build();
		lenient().when(productoRepository.findById(producto.getIdProducto())).thenReturn(Optional.of(producto));
		Tienda tienda = TiendaTestBuilder.builder().idTienda(1L).nombre("Tienda 1").build();
		lenient().when(tiendaRepository.findById(tienda.getIdTienda())).thenReturn(Optional.of(tienda));
		ProductoPorTienda productoPorTienda = ProductoPorTiendaTestBuilder.builder().idProductoPorTienda(1L)
				.producto(producto).tienda(tienda).build();
		lenient().when(productoPorTiendaRepository.findById(productoPorTienda.getIdProductoPorTienda()))
				.thenReturn(Optional.empty());
		PedidoDetalle pedidoDetalle = PedidoDetalleTestBuilder.builder().productoPorTienda(productoPorTienda)
				.cantidad(1).build();
		PedidoDetalleDTO pedidoDetalleDTO = PedidoDetalleDTO.builder()
				.productoPorTiendaIdProductoPorTienda(pedidoDetalle.getProductoPorTienda().getIdProductoPorTienda())
				.cantidad(pedidoDetalle.getCantidad()).build();
		List<PedidoDetalle> detalles = Lists.newArrayList(pedidoDetalle);
		List<PedidoDetalleDTO> detallesDTO = Lists.newArrayList(pedidoDetalleDTO);
		PedidoCabecera pedidoCabecera = PedidoCabeceraTestBuilder.builder().cliente(cliente).fechaPedido(new Date())
				.detalles(detalles).build();
		PedidoCabeceraDTO pedidoCabeceraDTO = PedidoCabeceraDTO.builder()
				.idCliente(pedidoCabecera.getCliente().getIdCliente()).fechaPedido(pedidoCabecera.getFechaPedido())
				.detalles(detallesDTO).build();
		Assertions.assertThrows(ResourceNotFoundException.class, () -> pedidoCabeceraService.guardarPedido(pedidoCabeceraDTO));
	}

	@Test
	void PedidoClienteNoExistente() {
		Cliente cliente = ClienteTestBuilder.builder().idCliente(1L).identificacion("0000000000")
				.nombre("NOMBRE CLIENTE").urlFoto("URL FOTO CLIENTE").build();
		lenient().when(clienteRepository.findById(cliente.getIdCliente())).thenReturn(Optional.empty());
		Producto producto = ProductoTestBuilder.builder().idProducto(1L).codigo("prod-1").nombre("prod-name-1")
				.precio(5.5f).stock(10).build();
		lenient().when(productoRepository.findById(producto.getIdProducto())).thenReturn(Optional.of(producto));
		Tienda tienda = TiendaTestBuilder.builder().idTienda(1L).nombre("Tienda 1").build();
		lenient().when(tiendaRepository.findById(tienda.getIdTienda())).thenReturn(Optional.of(tienda));
		ProductoPorTienda productoPorTienda = ProductoPorTiendaTestBuilder.builder().idProductoPorTienda(1L)
				.producto(producto).tienda(tienda).build();
		lenient().when(productoPorTiendaRepository.findById(productoPorTienda.getIdProductoPorTienda()))
				.thenReturn(Optional.of(productoPorTienda));
		PedidoDetalle pedidoDetalle = PedidoDetalleTestBuilder.builder().productoPorTienda(productoPorTienda)
				.cantidad(1).build();
		PedidoDetalleDTO pedidoDetalleDTO = PedidoDetalleDTO.builder()
				.productoPorTiendaIdProductoPorTienda(pedidoDetalle.getProductoPorTienda().getIdProductoPorTienda())
				.cantidad(pedidoDetalle.getCantidad()).build();
		List<PedidoDetalle> detalles = Lists.newArrayList(pedidoDetalle);
		List<PedidoDetalleDTO> detallesDTO = Lists.newArrayList(pedidoDetalleDTO);
		PedidoCabecera pedidoCabecera = PedidoCabeceraTestBuilder.builder().cliente(cliente).fechaPedido(new Date())
				.detalles(detalles).build();
		PedidoCabeceraDTO pedidoCabeceraDTO = PedidoCabeceraDTO.builder()
				.idCliente(pedidoCabecera.getCliente().getIdCliente()).fechaPedido(pedidoCabecera.getFechaPedido())
				.detalles(detallesDTO).build();
		Assertions.assertThrows(ResourceNotFoundException.class, () -> pedidoCabeceraService.guardarPedido(pedidoCabeceraDTO));
	}
	
	@Test
	void PedidoStockMayorDiez() {
		Cliente cliente = ClienteTestBuilder.builder().idCliente(1L).identificacion("0000000000")
				.nombre("NOMBRE CLIENTE").urlFoto("URL FOTO CLIENTE").build();
		lenient().when(clienteRepository.findById(cliente.getIdCliente())).thenReturn(Optional.of(cliente));
		Producto producto = ProductoTestBuilder.builder().idProducto(1L).codigo("prod-1").nombre("prod-name-1")
				.precio(5.5f).stock(10).build();
		lenient().when(productoRepository.findById(producto.getIdProducto())).thenReturn(Optional.of(producto));
		Tienda tienda = TiendaTestBuilder.builder().idTienda(1L).nombre("Tienda 1").build();
		lenient().when(tiendaRepository.findById(tienda.getIdTienda())).thenReturn(Optional.of(tienda));
		ProductoPorTienda productoPorTienda = ProductoPorTiendaTestBuilder.builder().idProductoPorTienda(1L)
				.producto(producto).tienda(tienda).build();
		lenient().when(productoPorTiendaRepository.findById(productoPorTienda.getIdProductoPorTienda()))
				.thenReturn(Optional.of(productoPorTienda));
		PedidoDetalle pedidoDetalle = PedidoDetalleTestBuilder.builder().productoPorTienda(productoPorTienda)
				.cantidad(21).build();
		PedidoDetalleDTO pedidoDetalleDTO = PedidoDetalleDTO.builder()
				.productoPorTiendaIdProductoPorTienda(pedidoDetalle.getProductoPorTienda().getIdProductoPorTienda())
				.cantidad(pedidoDetalle.getCantidad()).build();
		List<PedidoDetalle> detalles = Lists.newArrayList(pedidoDetalle);
		List<PedidoDetalleDTO> detallesDTO = Lists.newArrayList(pedidoDetalleDTO);
		PedidoCabecera pedidoCabecera = PedidoCabeceraTestBuilder.builder().cliente(cliente).fechaPedido(new Date())
				.detalles(detalles).build();
		PedidoCabeceraDTO pedidoCabeceraDTO = PedidoCabeceraDTO.builder()
				.idCliente(pedidoCabecera.getCliente().getIdCliente()).fechaPedido(pedidoCabecera.getFechaPedido())
				.detalles(detallesDTO).build();
		Assertions.assertThrows(UnprocessableEntityException.class, () -> pedidoCabeceraService.guardarPedido(pedidoCabeceraDTO));
	}
	

	@Test
	void crearPedidoStockEntreCincoDiez() {
		Cliente cliente = ClienteTestBuilder.builder().idCliente(1L).identificacion("0000000000")
				.nombre("NOMBRE CLIENTE").urlFoto("URL FOTO CLIENTE").build();
		lenient().when(clienteRepository.findById(cliente.getIdCliente())).thenReturn(Optional.of(cliente));
		Producto producto = ProductoTestBuilder.builder().idProducto(1L).codigo("prod-1").nombre("prod-name-1")
				.precio(5.5f).stock(10).build();
		lenient().when(productoRepository.findById(producto.getIdProducto())).thenReturn(Optional.of(producto));
		Tienda tienda = TiendaTestBuilder.builder().idTienda(1L).nombre("Tienda 1").build();
		lenient().when(tiendaRepository.findById(tienda.getIdTienda())).thenReturn(Optional.of(tienda));
		ProductoPorTienda productoPorTienda = ProductoPorTiendaTestBuilder.builder().idProductoPorTienda(1L)
				.producto(producto).tienda(tienda).build();
		lenient().when(productoPorTiendaRepository.findById(productoPorTienda.getIdProductoPorTienda()))
				.thenReturn(Optional.of(productoPorTienda));
		PedidoDetalle pedidoDetalle = PedidoDetalleTestBuilder.builder().productoPorTienda(productoPorTienda)
				.cantidad(17).build();
		PedidoDetalleDTO pedidoDetalleDTO = PedidoDetalleDTO.builder()
				.productoPorTiendaIdProductoPorTienda(pedidoDetalle.getProductoPorTienda().getIdProductoPorTienda())
				.cantidad(pedidoDetalle.getCantidad()).build();
		List<PedidoDetalle> detalles = Lists.newArrayList(pedidoDetalle);
		List<PedidoDetalleDTO> detallesDTO = Lists.newArrayList(pedidoDetalleDTO);
		PedidoCabecera pedidoCabecera = PedidoCabeceraTestBuilder.builder().cliente(cliente).fechaPedido(new Date())
				.detalles(detalles).build();
		PedidoCabeceraDTO pedidoCabeceraDTO = PedidoCabeceraDTO.builder()
				.idCliente(pedidoCabecera.getCliente().getIdCliente()).fechaPedido(pedidoCabecera.getFechaPedido())
				.detalles(detallesDTO).build();
		lenient().when(pedidoCabeceraRepository.save(Mockito.any(PedidoCabecera.class))).thenReturn(pedidoCabecera);
		pedidoCabeceraService.guardarPedido(pedidoCabeceraDTO);
		ArgumentCaptor<PedidoCabecera> pedidoCaptor = ArgumentCaptor.forClass(PedidoCabecera.class);
		Mockito.verify(pedidoCabeceraRepository).save(pedidoCaptor.capture());
		Mockito.verify(modelMapper).map(pedidoCabecera, PedidoCabeceraDTO.class);
		assertEquals(pedidoCabecera.getFechaPedido(), pedidoCaptor.getValue().getFechaPedido());
	}

	@Test
	void crearPedidoStockMenorCinco() {
		Cliente cliente = ClienteTestBuilder.builder().idCliente(1L).identificacion("0000000000")
				.nombre("NOMBRE CLIENTE").urlFoto("URL FOTO CLIENTE").build();
		lenient().when(clienteRepository.findById(cliente.getIdCliente())).thenReturn(Optional.of(cliente));
		Producto producto = ProductoTestBuilder.builder().idProducto(1L).codigo("prod-1").nombre("prod-name-1")
				.precio(5.5f).stock(10).build();
		lenient().when(productoRepository.findById(producto.getIdProducto())).thenReturn(Optional.of(producto));
		Tienda tienda = TiendaTestBuilder.builder().idTienda(1L).nombre("Tienda 1").build();
		lenient().when(tiendaRepository.findById(tienda.getIdTienda())).thenReturn(Optional.of(tienda));
		ProductoPorTienda productoPorTienda = ProductoPorTiendaTestBuilder.builder().idProductoPorTienda(1L)
				.producto(producto).tienda(tienda).build();
		lenient().when(productoPorTiendaRepository.findById(productoPorTienda.getIdProductoPorTienda()))
				.thenReturn(Optional.of(productoPorTienda));
		PedidoDetalle pedidoDetalle = PedidoDetalleTestBuilder.builder().productoPorTienda(productoPorTienda)
				.cantidad(12).build();
		PedidoDetalleDTO pedidoDetalleDTO = PedidoDetalleDTO.builder()
				.productoPorTiendaIdProductoPorTienda(pedidoDetalle.getProductoPorTienda().getIdProductoPorTienda())
				.cantidad(pedidoDetalle.getCantidad()).build();
		List<PedidoDetalle> detalles = Lists.newArrayList(pedidoDetalle);
		List<PedidoDetalleDTO> detallesDTO = Lists.newArrayList(pedidoDetalleDTO);
		PedidoCabecera pedidoCabecera = PedidoCabeceraTestBuilder.builder().cliente(cliente).fechaPedido(new Date())
				.detalles(detalles).build();
		PedidoCabeceraDTO pedidoCabeceraDTO = PedidoCabeceraDTO.builder()
				.idCliente(pedidoCabecera.getCliente().getIdCliente()).fechaPedido(pedidoCabecera.getFechaPedido())
				.detalles(detallesDTO).build();
		lenient().when(pedidoCabeceraRepository.save(Mockito.any(PedidoCabecera.class))).thenReturn(pedidoCabecera);
		pedidoCabeceraService.guardarPedido(pedidoCabeceraDTO);
		ArgumentCaptor<PedidoCabecera> pedidoCaptor = ArgumentCaptor.forClass(PedidoCabecera.class);
		Mockito.verify(pedidoCabeceraRepository).save(pedidoCaptor.capture());
		Mockito.verify(modelMapper).map(pedidoCabecera, PedidoCabeceraDTO.class);
		assertEquals(pedidoCabecera.getFechaPedido(), pedidoCaptor.getValue().getFechaPedido());
	}
	
}
