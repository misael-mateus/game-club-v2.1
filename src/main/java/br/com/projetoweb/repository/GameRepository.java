package br.com.projetoweb.repository;

import br.com.projetoweb.model.Console;
import br.com.projetoweb.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    Optional<Game> findByNameAndConsole(String name, Console console);
}
