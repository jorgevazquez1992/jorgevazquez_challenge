package devsu.challenge.bank.controller;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import devsu.challenge.bank.dto.ProductoDTO;
import devsu.challenge.bank.service.ProductoService;

@RestController
@RequestMapping("/producto")
public class ProductoController {

	private ProductoService productoService;
	
	public ProductoController(ProductoService productoService) {
		this.productoService = productoService;
	}
	
	@GetMapping
	public List<ProductoDTO> listarTodos(){
		return productoService.buscarTodos();
	}
	
	@PutMapping(value="{id}/{stock}")
	public ResponseEntity<ProductoDTO> actualizarStock(@Param("id") Long id, @Param("stock") Integer stock){
		ProductoDTO respuesta = productoService.actualizarStock(id, stock);
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}
	
}
