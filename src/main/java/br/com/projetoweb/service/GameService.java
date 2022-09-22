package br.com.projetoweb.service;

import br.com.projetoweb.dto.GameDTO;
import br.com.projetoweb.model.Console;
import br.com.projetoweb.model.Game;
import br.com.projetoweb.model.GamePartner;
import br.com.projetoweb.repository.GameRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class GameService {
    private final GameRepository gameRepository;
    private final GamePartnerService gamePartnerService;

    @Autowired
    public GameService(GameRepository gameRepository, GamePartnerService gamePartnerService) {
        this.gameRepository = gameRepository;
        this.gamePartnerService = gamePartnerService;
    }

    @Transactional
    public void newGame(GameDTO... gameDTO) {
        for (GameDTO newGame : gameDTO) {
            Game game = new Game();
            BeanUtils.copyProperties(newGame, game);
            this.gameRepository.save(game);
        }
    }

    @Transactional
    public Game newGame(GameDTO gameDTO) {
        Game game = new Game();
        BeanUtils.copyProperties(gameDTO, game);
        return this.gameRepository.save(game);
    }

    @Transactional
    public void deleteGame(Long id) {
        this.gameRepository.deleteById(id);
    }

    public Page<Game> findAll(Pageable page) {
        return this.gameRepository.findAll(page);
    }

    public Optional<Game> findById(Long id) {
        return this.gameRepository.findById(id);
    }

    public Optional<Game> findGameByNameAndConsole(String name, Console console) {
        return this.gameRepository.findByNameAndConsole(name, console);
    }

    @Transactional
    public Optional<Game> alterGame(Long id, GameDTO gameDTO) {
        Optional<Game> gamerModel = this.gameRepository.findById(id);
        gamerModel.ifPresent(game -> {
            game.setName(gameDTO.getName());
            game.setConsole(gameDTO.getConsole());
        });
        return gamerModel;
    }

    public Set<Game> findByGamesDtos(Set<GameDTO> gameDTOS) {
        Set<Game> games = new HashSet<>();
        for (GameDTO gameDTO : gameDTOS) {
            Optional<Game> game = this.findGameByNameAndConsole(gameDTO.getName(), gameDTO.getConsole());
            if (game.isPresent()) {
                games.add(game.get());
            } else {
                games.add(this.newGame(gameDTO));
            }
        }
        return games;
    }

    public Page<Game> findAllGamesFindByPartnerId(Long partnerId, Pageable pageable) {
        Set<GamePartner> allGamesFindByPartnerId = this.gamePartnerService.findAllGamesFindByPartnerId(partnerId);
        List<Game> games = new ArrayList<>(allGamesFindByPartnerId.stream().map(GamePartner::getGame).toList());
        return PageableExecutionUtils.getPage(games, pageable, games::size);
    }

}
