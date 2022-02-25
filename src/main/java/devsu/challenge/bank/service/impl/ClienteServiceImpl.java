package devsu.challenge.bank.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import devsu.challenge.bank.dto.ClienteDTO;
import devsu.challenge.bank.exception.ResourceNotFoundException;
import devsu.challenge.bank.model.Cliente;
import devsu.challenge.bank.repository.ClienteRepository;
import devsu.challenge.bank.service.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService {

	private final ClienteRepository clienteRepository;
	private final ModelMapper modelMapper;

	@Autowired
	public ClienteServiceImpl(@Autowired ClienteRepository clienteRepository, ModelMapper modelMapper) {
		this.clienteRepository = clienteRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	@Transactional
	public ClienteDTO crear(ClienteDTO clienteDTO) {
		Cliente cliente = modelMapper.map(clienteDTO, Cliente.class);
		cliente = clienteRepository.save(cliente);
		return modelMapper.map(cliente, ClienteDTO.class);
	}

	@Override
	@Transactional
	public ClienteDTO actualizar(ClienteDTO clienteDTO) {
		Cliente cliente = buscarClientePorId(clienteDTO.getIdCliente());
		cliente.setIdentificacion(clienteDTO.getIdentificacion());
		cliente.setNombre(clienteDTO.getNombre());
		cliente.setUrlFoto(clienteDTO.getUrlFoto());
		cliente = clienteRepository.save(cliente);
		return modelMapper.map(cliente, ClienteDTO.class);
	}
	
	@Override
	@Transactional
	public Long delete(Long idCliente) {
		Cliente cliente = buscarClientePorId(idCliente);
		clienteRepository.delete(cliente);
		return cliente.getIdCliente();
	}
	
	@Override
    @Transactional(readOnly = true)
	public List<ClienteDTO> listarTodos(){
		List<Cliente> clientes = clienteRepository.findAll();
		return clientes.stream().map(cliente -> modelMapper.map(cliente, ClienteDTO.class)).collect(Collectors.toList());
	}
	
	@Override
    @Transactional(readOnly = true)
	public ClienteDTO buscarPorId(Long idCliente) {
		return modelMapper.map(buscarClientePorId(idCliente), ClienteDTO.class);
	}
	
	private Cliente buscarClientePorId(Long clienteId) {
		return clienteRepository.findById(clienteId).orElseThrow(()-> new ResourceNotFoundException("Cliente no encontrado"));
	}
	
}
