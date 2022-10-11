package br.com.projetoweb.repository;

import br.com.projetoweb.model.GameLoan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;


public interface GameLoanRepository extends JpaRepository<GameLoan, Long> {

    Set<GameLoan> findAllByPartnerId(Long partnerId);
}
