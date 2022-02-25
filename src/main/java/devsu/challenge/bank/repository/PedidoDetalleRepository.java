package devsu.challenge.bank.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import devsu.challenge.bank.model.PedidoDetalle;

@Repository
public interface PedidoDetalleRepository extends JpaRepository<PedidoDetalle, Long>{
	@Query("from PedidoDetalle where productoPorTienda.tienda.idTienda = :idTienda")
	Optional<List<PedidoDetalle>> findByTienda(@Param("idTienda") Long idTienda);
}
