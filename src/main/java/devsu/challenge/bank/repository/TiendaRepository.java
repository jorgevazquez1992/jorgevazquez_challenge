package devsu.challenge.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import devsu.challenge.bank.model.Tienda;

@Repository
public interface TiendaRepository extends JpaRepository<Tienda, Long>{
}
