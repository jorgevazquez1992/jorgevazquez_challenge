package devsu.challenge.bank.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;

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

import devsu.challenge.bank.dto.ClienteDTO;
import devsu.challenge.bank.dto.ProductoDTO;
import devsu.challenge.bank.exception.ResourceNotFoundException;
import devsu.challenge.bank.model.Producto;
import devsu.challenge.bank.repository.ProductoRepository;
import devsu.challenge.bank.service.impl.ProductoServiceImpl;
import testbuilders.ProductoTestBuilder;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceTest {

	@InjectMocks
	private ProductoServiceImpl productoService;
	@Mock
	private ProductoRepository productoRepository;
	@Mock
	private ModelMapper modelMapper;

	@Test
	void listarProductos() {
		Producto producto1 = ProductoTestBuilder.builder().codigo("prod-1").nombre("prod-name-1")
				.precio(5.5f).stock(10).build();
		Producto producto2 = ProductoTestBuilder.builder().codigo("prod-2").nombre("prod-name-2")
				.precio(6f).stock(5).build();
		List<Producto> productos = Lists.newArrayList(producto1, producto2);
		lenient().when(productoRepository.findAll()).thenReturn(productos);
		productoService.buscarTodos();
		Mockito.verify(productoRepository).findAll();
		Mockito.verify(modelMapper).map(productos.get(0), ProductoDTO.class);
		Mockito.verify(modelMapper).map(productos.get(1), ProductoDTO.class);
	}

	@Test
	void actualizarStock() {
		Integer nuevoStock = 5;
		Producto producto = ProductoTestBuilder.builder().idProducto(1L).codigo("prod-1").nombre("prod-name-1")
				.precio(5.5f).stock(10).build();
		lenient().when(productoRepository.findById(producto.getIdProducto())).thenReturn(Optional.of(producto));
		productoService.actualizarStock(producto.getIdProducto(), nuevoStock);
		ArgumentCaptor<Producto> productoCaptor = ArgumentCaptor.forClass(Producto.class);
		Mockito.verify(productoRepository).save(productoCaptor.capture());
		assertEquals(producto.getStock(), productoCaptor.getValue().getStock());
	}
	
	@Test
	void actualizarStockProductoNoEncontrado() {
		Integer nuevoStock = 5;
		Producto producto = ProductoTestBuilder.builder().idProducto(1L).codigo("prod-1").nombre("prod-name-1")
				.precio(5.5f).stock(10).build();
		lenient().when(productoRepository.findById(producto.getIdProducto())).thenReturn(Optional.empty());
		Assertions.assertThrows(ResourceNotFoundException.class, () -> productoService.actualizarStock(producto.getIdProducto(), nuevoStock));
	}

}
