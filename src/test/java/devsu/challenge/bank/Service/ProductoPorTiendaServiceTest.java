package devsu.challenge.bank.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import devsu.challenge.bank.dto.ProductoPorTiendaDTO;
import devsu.challenge.bank.exception.ResourceNotFoundException;
import devsu.challenge.bank.exception.UnprocessableEntityException;
import devsu.challenge.bank.model.Producto;
import devsu.challenge.bank.model.ProductoPorTienda;
import devsu.challenge.bank.model.Tienda;
import devsu.challenge.bank.repository.ProductoPorTiendaRepository;
import devsu.challenge.bank.repository.ProductoRepository;
import devsu.challenge.bank.repository.TiendaRepository;
import devsu.challenge.bank.service.impl.ProductoPorTiendaServiceImpl;
import testbuilders.ProductoPorTiendaTestBuilder;
import testbuilders.ProductoTestBuilder;
import testbuilders.TiendaTestBuilder;

@ExtendWith(MockitoExtension.class)
public class ProductoPorTiendaServiceTest {

	@InjectMocks
	private ProductoPorTiendaServiceImpl productoPorTiendaService;
	@Mock
	private ProductoPorTiendaRepository productoPorTiendaRepository;
	@Mock
	private ProductoRepository productoRepository;
	@Mock
	private TiendaRepository tiendaRepository;
	@Mock
	private ModelMapper modelMapper;

	@Test
	void asignarProductoATiendaExito() {
		Producto producto = ProductoTestBuilder.builder().idProducto(1L).codigo("prod-1").nombre("prod-name-1")
				.precio(5.5f).stock(10).build();
		Tienda tienda = TiendaTestBuilder.builder().idTienda(1L).nombre("Tienda 1").build();
		ProductoPorTienda productoPorTienda = ProductoPorTiendaTestBuilder.builder().producto(producto).tienda(tienda)
				.build();
		ProductoPorTiendaDTO productoPorTiendaDTO = ProductoPorTiendaDTO.builder()
				.productoIdProducto(productoPorTienda.getProducto().getIdProducto())
				.tiendaIdTienda(productoPorTienda.getTienda().getIdTienda()).build();
		lenient().when(modelMapper.map(productoPorTiendaDTO, ProductoPorTienda.class)).thenReturn(productoPorTienda);
		lenient().when(productoRepository.findById(producto.getIdProducto())).thenReturn(Optional.of(producto));
		lenient().when(tiendaRepository.findById(tienda.getIdTienda())).thenReturn(Optional.of(tienda));
		lenient().when(productoPorTiendaRepository.save(Mockito.any(ProductoPorTienda.class))).thenReturn(productoPorTienda);
		
		productoPorTiendaService.asignarProductoATienda(productoPorTiendaDTO);
		
		ArgumentCaptor<ProductoPorTienda> productoPorTiendaCaptor = ArgumentCaptor.forClass(ProductoPorTienda.class);
		Mockito.verify(productoRepository).findById(productoPorTiendaDTO.getProductoIdProducto());
		Mockito.verify(tiendaRepository).findById(productoPorTiendaDTO.getTiendaIdTienda());
		Mockito.verify(productoPorTiendaRepository).save(productoPorTiendaCaptor.capture());
		Mockito.verify(modelMapper).map(productoPorTienda, ProductoPorTiendaDTO.class);
		
		assertEquals(productoPorTienda.getProducto().getIdProducto(), productoPorTiendaCaptor.getValue().getProducto().getIdProducto());
		assertEquals(productoPorTienda.getTienda().getIdTienda(), productoPorTiendaCaptor.getValue().getTienda().getIdTienda());
	}
	
	@Test
	void asignarProductoTiendaNoEncontrada() {
		Producto producto = ProductoTestBuilder.builder().idProducto(1L).codigo("prod-1").nombre("prod-name-1")
				.precio(5.5f).stock(10).build();
		Tienda tienda = TiendaTestBuilder.builder().idTienda(1L).nombre("Tienda 1").build();
		ProductoPorTienda productoPorTienda = ProductoPorTiendaTestBuilder.builder().producto(producto).tienda(tienda)
				.build();
		ProductoPorTiendaDTO productoPorTiendaDTO = ProductoPorTiendaDTO.builder()
				.productoIdProducto(productoPorTienda.getProducto().getIdProducto())
				.tiendaIdTienda(productoPorTienda.getTienda().getIdTienda()).build();
		lenient().when(productoRepository.findById(producto.getIdProducto())).thenReturn(Optional.of(producto));
		lenient().when(tiendaRepository.findById(tienda.getIdTienda())).thenReturn(Optional.empty());
		Assertions.assertThrows(ResourceNotFoundException.class, () -> productoPorTiendaService.asignarProductoATienda(productoPorTiendaDTO));
	}
	
	@Test
	void asignarProductoNoEncontrado() {
		Producto producto = ProductoTestBuilder.builder().idProducto(1L).codigo("prod-1").nombre("prod-name-1")
				.precio(5.5f).stock(10).build();
		Tienda tienda = TiendaTestBuilder.builder().idTienda(1L).nombre("Tienda 1").build();
		ProductoPorTienda productoPorTienda = ProductoPorTiendaTestBuilder.builder().producto(producto).tienda(tienda)
				.build();
		ProductoPorTiendaDTO productoPorTiendaDTO = ProductoPorTiendaDTO.builder()
				.productoIdProducto(productoPorTienda.getProducto().getIdProducto())
				.tiendaIdTienda(productoPorTienda.getTienda().getIdTienda()).build();
		lenient().when(productoRepository.findById(producto.getIdProducto())).thenReturn(Optional.empty());
		lenient().when(tiendaRepository.findById(tienda.getIdTienda())).thenReturn(Optional.of(tienda));
		Assertions.assertThrows(ResourceNotFoundException.class, () -> productoPorTiendaService.asignarProductoATienda(productoPorTiendaDTO));
	}
	
	@Test
	void asignacionYaExistente() {
		Producto producto = ProductoTestBuilder.builder().idProducto(1L).codigo("prod-1").nombre("prod-name-1")
				.precio(5.5f).stock(10).build();
		Tienda tienda = TiendaTestBuilder.builder().idTienda(1L).nombre("Tienda 1").build();
		ProductoPorTienda productoPorTienda = ProductoPorTiendaTestBuilder.builder().idProductoPorTienda(1L).producto(producto).tienda(tienda)
				.build();
		ProductoPorTiendaDTO productoPorTiendaDTO = ProductoPorTiendaDTO.builder().idProductoPorTienda(productoPorTienda.getIdProductoPorTienda())
				.productoIdProducto(productoPorTienda.getProducto().getIdProducto())
				.tiendaIdTienda(productoPorTienda.getTienda().getIdTienda()).build();
		lenient().when(productoPorTiendaRepository.findByProductoAndTienda(producto.getIdProducto(),tienda.getIdTienda())).thenReturn(Optional.of(productoPorTienda));
		Assertions.assertThrows(UnprocessableEntityException.class, () -> productoPorTiendaService.asignarProductoATienda(productoPorTiendaDTO));
		
	}

}
