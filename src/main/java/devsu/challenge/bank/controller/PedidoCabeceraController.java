package devsu.challenge.bank.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import devsu.challenge.bank.dto.PedidoCabeceraDTO;
import devsu.challenge.bank.service.PedidoCabeceraService;

@RestController
@RequestMapping("/pedidoCabecera")
public class PedidoCabeceraController {

	private PedidoCabeceraService pedidoCabeceraService;
	
	@Autowired
	public PedidoCabeceraController(PedidoCabeceraService pedidoCabeceraService) {
		this.pedidoCabeceraService = pedidoCabeceraService;
	}
	
	@PostMapping
	public ResponseEntity<PedidoCabeceraDTO> crear(@RequestBody @Valid PedidoCabeceraDTO pedidoCabeceraDTO) {
		PedidoCabeceraDTO respuesta = pedidoCabeceraService.guardarPedido(pedidoCabeceraDTO);
		return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
	}
	
}
