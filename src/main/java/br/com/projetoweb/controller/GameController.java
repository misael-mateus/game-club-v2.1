package br.com.projetoweb.controller;

import br.com.projetoweb.dto.GameDTO;
import br.com.projetoweb.model.Game;
import br.com.projetoweb.service.GameService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v2/game")
@Api(value = "Game API", tags = {"Game"})
@CrossOrigin(origins = "*")
public class GameController {
    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    @CacheEvict(value = "lista_de_games", allEntries = true)
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Game> newGame(@RequestBody GameDTO... game) {
        try {
            this.gameService.newGame(game);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}")
    @CacheEvict(value = "lista_de_games", allEntries = true)
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Game> alterGame(@PathVariable Long id,@RequestBody
    GameDTO game) {
        try {
            return ResponseEntity.ok(this.gameService.alterGame(id, game).get());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "lista_de_games", allEntries = true)
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Game> deleteGame(@PathVariable Long id) {
        try {
            this.gameService.deleteGame(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.LOCKED).build();
        }
    }

    @GetMapping
    @Cacheable(value = "lista_de_games")
    //@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Page<Game>> findAllGames(Pageable page) {
        try {
            return ResponseEntity.ok(this.gameService.findAll(page));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{id}")
    //@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Game> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(this.gameService.findById(id).get());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/findAll/{partnerId}")
    //@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Page<Game>> findAllByPartnerId(@PathVariable Long partnerId, Pageable page) {
        try {
            return ResponseEntity.ok(this.gameService.findAllGamesFindByPartnerId(partnerId,page));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
