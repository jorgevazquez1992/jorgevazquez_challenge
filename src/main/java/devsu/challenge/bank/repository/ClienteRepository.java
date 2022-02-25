package devsu.challenge.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import devsu.challenge.bank.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{	
}
