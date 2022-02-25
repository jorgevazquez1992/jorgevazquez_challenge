package devsu.challenge.bank.service;

import java.util.List;

import devsu.challenge.bank.dto.ProductoDTO;

public interface ProductoService {

	List<ProductoDTO> buscarTodos();

	ProductoDTO actualizarStock(Long idProducto, Integer stock);

}
