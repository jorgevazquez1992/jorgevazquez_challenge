package devsu.challenge.bank.service;

import java.util.List;

import javax.management.BadAttributeValueExpException;

import devsu.challenge.bank.dto.ClienteDTO;

public interface ClienteService {

	ClienteDTO crear(ClienteDTO clienteDTO) throws BadAttributeValueExpException;

	ClienteDTO actualizar(ClienteDTO clienteDTO);

	Long delete(Long idCliente);

	List<ClienteDTO> listarTodos();

	ClienteDTO buscarPorId(Long idCliente);

}
