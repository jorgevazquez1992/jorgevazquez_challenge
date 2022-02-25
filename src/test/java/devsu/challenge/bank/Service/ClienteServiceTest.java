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

import devsu.challenge.bank.dto.ClienteDTO;
import devsu.challenge.bank.exception.ResourceNotFoundException;
import devsu.challenge.bank.model.Cliente;
import devsu.challenge.bank.repository.ClienteRepository;
import devsu.challenge.bank.service.impl.ClienteServiceImpl;
import testbuilders.ClienteTestBuilder;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

	@InjectMocks
	private ClienteServiceImpl clienteService;
	@Mock
	private ClienteRepository clienteRepository;
	@Mock
	private ModelMapper modelMapper;

	@Test
	void crearClienteExito() {
		String identificacion = "0000000000";
		String nombre = "NOMBRE CLIENTE";
		String urlFoto = "URL FOTO CLIENTE";
		Cliente cliente = ClienteTestBuilder.builder().identificacion(identificacion).nombre(nombre).urlFoto(urlFoto)
				.build();
		ClienteDTO clienteDTO = ClienteDTO.builder().identificacion(cliente.getIdentificacion())
				.nombre(cliente.getNombre()).urlFoto(cliente.getUrlFoto()).build();
		lenient().when(modelMapper.map(clienteDTO, Cliente.class)).thenReturn(cliente);
		lenient().when(clienteRepository.save(Mockito.any(Cliente.class))).thenReturn(cliente);
		clienteService.crear(clienteDTO);
		ArgumentCaptor<Cliente> clienteCaptor = ArgumentCaptor.forClass(Cliente.class);
		Mockito.verify(modelMapper).map(clienteDTO, Cliente.class);
		Mockito.verify(clienteRepository).save(clienteCaptor.capture());
		Mockito.verify(modelMapper).map(cliente, ClienteDTO.class);
		assertEquals(cliente.getIdentificacion(), clienteCaptor.getValue().getIdentificacion());
		assertEquals(cliente.getNombre(), clienteCaptor.getValue().getNombre());
		assertEquals(cliente.getUrlFoto(), clienteCaptor.getValue().getUrlFoto());
	}

	@Test
	void actualizarClienteExito() {
		String nombre = "NUEVO NOMBRE";
		Cliente cliente = ClienteTestBuilder.builder().idCliente(1L).identificacion("0000000000")
				.nombre("NOMBRE CLIENTE").urlFoto("URL FOTO CLIENTE").build();
		lenient().when(clienteRepository.findById(cliente.getIdCliente())).thenReturn(Optional.of(cliente));
		ClienteDTO clienteDTO = ClienteDTO.builder().idCliente(cliente.getIdCliente()).identificacion(cliente.getIdentificacion())
				.nombre(nombre).urlFoto(cliente.getUrlFoto()).build();
		lenient().when(clienteRepository.save(Mockito.any(Cliente.class))).thenReturn(cliente);
		clienteService.actualizar(clienteDTO);
		Mockito.verify(clienteRepository).findById(clienteDTO.getIdCliente());
		Mockito.verify(modelMapper).map(cliente, ClienteDTO.class);
		assertEquals(cliente.getNombre(), nombre);	
	}
	
	@Test
	void actualizarClienteNoEncontrado() {
		Cliente cliente = ClienteTestBuilder.builder().idCliente(100L).identificacion("0000000000")
				.nombre("NOMBRE CLIENTE").urlFoto("URL FOTO CLIENTE").build();
		ClienteDTO clienteDTO = ClienteDTO.builder().identificacion(cliente.getIdentificacion())
				.nombre(cliente.getNombre()).urlFoto(cliente.getUrlFoto()).build();
		lenient().when(clienteRepository.findById(cliente.getIdCliente())).thenReturn(Optional.empty());
		Assertions.assertThrows(ResourceNotFoundException.class, () -> clienteService.actualizar(clienteDTO));		
	}

	@Test
	void eliminacionExitosa() {
		Cliente cliente = ClienteTestBuilder.builder().idCliente(1L).identificacion("0000000000")
				.nombre("NOMBRE CLIENTE").urlFoto("URL FOTO CLIENTE").build();
		ClienteDTO clienteDTO = ClienteDTO.builder().idCliente(1L).identificacion(cliente.getIdentificacion())
				.nombre(cliente.getNombre()).urlFoto(cliente.getUrlFoto()).build();
		lenient().when(clienteRepository.findById(cliente.getIdCliente())).thenReturn(Optional.of(cliente));
		assertEquals(clienteService.delete(cliente.getIdCliente()), clienteDTO.getIdCliente());
	}
	
	@Test
	void eliminarClienteNoEncontrado() {
		Cliente cliente = ClienteTestBuilder.builder().idCliente(1L).identificacion("0000000000")
				.nombre("NOMBRE CLIENTE").urlFoto("URL FOTO CLIENTE").build();
		Assertions.assertThrows(ResourceNotFoundException.class, () -> clienteService.delete(cliente.getIdCliente()));
	}
}
