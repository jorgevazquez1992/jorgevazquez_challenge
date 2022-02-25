package devsu.challenge.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import devsu.challenge.bank.model.PedidoCabecera;

@Repository
public interface PedidoCabeceraRepository extends JpaRepository<PedidoCabecera, Long>{
}
