package devsu.challenge.bank.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import devsu.challenge.bank.model.ProductoPorTienda;

@Repository
public interface ProductoPorTiendaRepository extends JpaRepository<ProductoPorTienda, Long>{
	@Query("from ProductoPorTienda where producto.idProducto = :idProducto and tienda.idTienda = :idTienda")
	Optional<ProductoPorTienda> findByProductoAndTienda(@Param("idProducto") Long idProducto,@Param("idTienda") Long idTienda);
}
