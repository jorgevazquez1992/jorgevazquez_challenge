package devsu.challenge.bank.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import devsu.challenge.bank.dto.ProductoDTO;
import devsu.challenge.bank.exception.ResourceNotFoundException;
import devsu.challenge.bank.model.Producto;
import devsu.challenge.bank.repository.ProductoRepository;
import devsu.challenge.bank.service.ProductoService;

@Service
public class ProductoServiceImpl implements ProductoService {

	private final ProductoRepository productoRepository;
	private final ModelMapper modelMapper;
	
	@Autowired
	public ProductoServiceImpl(@Autowired ProductoRepository productoRepository, ModelMapper modelMapper) {
		this.productoRepository = productoRepository;
		this.modelMapper = modelMapper;
	}
	
	@Override
    @Transactional(readOnly = true)
	public List<ProductoDTO> buscarTodos() {
		List<Producto> productos = productoRepository.findAll();
		return productos.stream().map(producto -> modelMapper.map(producto, ProductoDTO.class)).collect(Collectors.toList());
		
	}
	
	@Override
	@Transactional
	public ProductoDTO actualizarStock(Long idProducto, Integer stock) {
		Producto producto = buscarProductoPorId(idProducto);
		producto.setStock(producto.getStock()+stock);
		producto = productoRepository.save(producto);
		return modelMapper.map(producto, ProductoDTO.class);
	}
	
	private Producto buscarProductoPorId(Long idProducto) {
		return productoRepository.findById(idProducto).orElseThrow(()-> new ResourceNotFoundException("Producto no encontrado"));
	}
	
}
