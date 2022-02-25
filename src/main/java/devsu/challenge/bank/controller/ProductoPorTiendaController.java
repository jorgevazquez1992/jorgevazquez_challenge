package devsu.challenge.bank.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import devsu.challenge.bank.dto.ProductoPorTiendaDTO;
import devsu.challenge.bank.service.ProductoPorTiendaService;

public class ProductoPorTiendaController {

	private ProductoPorTiendaService productoPorTiendaService;

	@Autowired
	public ProductoPorTiendaController(ProductoPorTiendaService productoPorTiendaService) {
		this.productoPorTiendaService = productoPorTiendaService;
	}

	@PostMapping
	public ResponseEntity<ProductoPorTiendaDTO> asignar(@RequestBody @Valid ProductoPorTiendaDTO productoPorTiendaDTO) {
		ProductoPorTiendaDTO respuesta = productoPorTiendaService.asignarProductoATienda(productoPorTiendaDTO);
		return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
	}

}
