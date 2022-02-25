package devsu.challenge.bank.controller;

import java.util.List;

import javax.management.BadAttributeValueExpException;
import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import devsu.challenge.bank.dto.ClienteDTO;
import devsu.challenge.bank.service.ClienteService;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

	private ClienteService clienteService;
	
	@Autowired
	public ClienteController(ClienteService clienteService) {
		this.clienteService = clienteService;
	}
	
	@PostMapping
	public ResponseEntity<ClienteDTO> crear(@RequestBody @Valid ClienteDTO clienteDTO) throws BadAttributeValueExpException{
		ClienteDTO respuesta = clienteService.crear(clienteDTO);
		return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
	}
	
	@GetMapping
	public List<ClienteDTO> listarTodos(){
		return clienteService.listarTodos();
	}
	
	@PutMapping
	public ResponseEntity<ClienteDTO> actualizar(@RequestBody @Valid ClienteDTO clienteDTO) throws BadAttributeValueExpException{
		ClienteDTO respuesta = clienteService.actualizar(clienteDTO);
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}
	
	@DeleteMapping(value="{id}")
    public ResponseEntity<Long> delete(@Min(1) @PathVariable("id") long id) {
    	return new ResponseEntity<>(clienteService.delete(id), HttpStatus.OK);
    }
	
    @GetMapping(value="{id}")
    public ResponseEntity<ClienteDTO> getOne(@Min(1) @PathVariable("id") long id) {
    	ClienteDTO clienteDTO = clienteService.buscarPorId(id);
    	return new ResponseEntity<>(clienteDTO, HttpStatus.OK);
    }
	
}
