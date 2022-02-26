package devsu.challenge.bank.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import devsu.challenge.bank.dto.NumeroTransaccionesDTO;
import devsu.challenge.bank.model.PedidoDetalle;

@Repository
public interface PedidoDetalleRepository extends JpaRepository<PedidoDetalle, Long>{
	@Query("from PedidoDetalle where productoPorTienda.tienda.idTienda = :idTienda")
	Optional<List<PedidoDetalle>> findByTienda(@Param("idTienda") Long idTienda);
	
	@Query("SELECT new devsu.challenge.bank.dto.NumeroTransaccionesDTO(pd.productoPorTienda.tienda.idTienda, pd.productoPorTienda.tienda.nombre, pd.pedidoCabecera.fechaPedido, count(pd))"
			+ " FROM PedidoDetalle AS pd GROUP BY pd.productoPorTienda.tienda.idTienda, pd.productoPorTienda.tienda.nombre, pd.pedidoCabecera.fechaPedido")
	List<NumeroTransaccionesDTO> contarPorTiendaFecha();

	@Query("from PedidoDetalle where pedidoCabecera.cliente.idCliente = :idCliente and pedidoCabecera.fechaPedido >= :fechaInicio and pedidoCabecera.fechaPedido <= :fechaFin")
	Optional<List<PedidoDetalle>> buscarPorClienteRango(@Param("idCliente") Long idCliente, @Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin);
	
}
