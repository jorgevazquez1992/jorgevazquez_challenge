package devsu.challenge.bank.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import devsu.challenge.bank.dto.ProductoPorTiendaDTO;
import devsu.challenge.bank.exception.ResourceNotFoundException;
import devsu.challenge.bank.exception.UnprocessableEntityException;
import devsu.challenge.bank.model.Producto;
import devsu.challenge.bank.model.ProductoPorTienda;
import devsu.challenge.bank.model.Tienda;
import devsu.challenge.bank.repository.ProductoPorTiendaRepository;
import devsu.challenge.bank.repository.ProductoRepository;
import devsu.challenge.bank.repository.TiendaRepository;
import devsu.challenge.bank.service.ProductoPorTiendaService;

@Service
public class ProductoPorTiendaServiceImpl implements ProductoPorTiendaService {

	private final ProductoPorTiendaRepository productoPorTiendaRepository;
	private final ProductoRepository productoRepository;
	private final TiendaRepository tiendaRepository;
	private final ModelMapper modelMapper;

	@Autowired
	public ProductoPorTiendaServiceImpl(@Autowired ProductoPorTiendaRepository productoPorTiendaRepository,
			ProductoRepository productoRepository, TiendaRepository tiendaRepository, ModelMapper modelMapper) {
		this.productoPorTiendaRepository = productoPorTiendaRepository;
		this.productoRepository = productoRepository;
		this.tiendaRepository = tiendaRepository;
		this.modelMapper = modelMapper;
	}
	
	@Override
	@Transactional
	public ProductoPorTiendaDTO asignarProductoATienda(ProductoPorTiendaDTO productoPorTiendaDTO) {
		validarRelacionProductoTienda(productoPorTiendaDTO.getProductoIdProducto(), productoPorTiendaDTO.getTiendaIdTienda());
		Producto producto = buscarProductoPorId(productoPorTiendaDTO.getProductoIdProducto());
		Tienda tienda = buscarTiendaPorId(productoPorTiendaDTO.getTiendaIdTienda());
		ProductoPorTienda productoPorTienda = new ProductoPorTienda();
		productoPorTienda.setProducto(producto);
		productoPorTienda.setTienda(tienda);
		productoPorTienda = productoPorTiendaRepository.save(productoPorTienda);
		return modelMapper.map(productoPorTienda,ProductoPorTiendaDTO.class);
	}
	
	private Producto buscarProductoPorId(Long idProducto) {
		return productoRepository.findById(idProducto).orElseThrow(()-> new ResourceNotFoundException("Producto no encontrado"));
	}
	
	private Tienda buscarTiendaPorId(Long idTienda) {
		return tiendaRepository.findById(idTienda).orElseThrow(()-> new ResourceNotFoundException("Tienda no encontrada"));
	}
	
	private void validarRelacionProductoTienda(Long idProducto, Long idTienda) {
		ProductoPorTienda productoPorTienda = productoPorTiendaRepository.findByProductoAndTienda(idProducto, idTienda).orElse(null);
		if(productoPorTienda != null) {
			throw new UnprocessableEntityException("Relacion ya existente"); 
		}
	}

}
