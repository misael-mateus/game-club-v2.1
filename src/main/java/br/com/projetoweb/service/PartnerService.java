package br.com.projetoweb.service;

import br.com.projetoweb.dto.GameDTO;
import br.com.projetoweb.dto.PartnerDTO;
import br.com.projetoweb.model.Game;
import br.com.projetoweb.model.GamePartner;
import br.com.projetoweb.model.Partner;
import br.com.projetoweb.repository.PartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

@Service
public class PartnerService {

    private final PartnerRepository partnerRepository;

    private final GamePartnerService gamePartnerService;

    private final GameService gameService;

    @Autowired
    public PartnerService(PartnerRepository partnerRepository, GamePartnerService gamePartnerService, GameService gameService) {
        this.partnerRepository = partnerRepository;
        this.gamePartnerService = gamePartnerService;
        this.gameService = gameService;
    }

    @Transactional
    public Partner newPartner(PartnerDTO partnerDTO) {
        Partner partner = new Partner();
        partner.setName(partnerDTO.getName());
        partner.setPhoneNumber(partnerDTO.getPhoneNumber());
        Set<Game> games = this.gameService.findByGamesDtos(partnerDTO.getGames());
        Partner partnerDB = this.partnerRepository.save(partner);
        for (Game game : games) {
            GamePartner gamePartner = new GamePartner();
            partnerDB.setGames(gamePartner);
            game.setPartners(gamePartner);
            gamePartner.setPartner(partnerDB);
            gamePartner.setGame(game);
            this.gamePartnerService.newGamePartner(gamePartner);
        }
        return partnerDB;
    }

    @Transactional
    public Partner deletePartner(Long id) {
        Partner partner = this.partnerRepository.findById(id).orElseThrow(() -> new RuntimeException("Partner not found"));
        this.gamePartnerService.deleteGamePartnerByPartner(partner);
        this.partnerRepository.delete(partner);
        return partner;
    }

    @Transactional
    public void deleteGamesToPartner(Long idPartner,Set<GameDTO> gamesDTO) {
        Optional<Partner> partner = this.partnerRepository.findById(idPartner);
        if (partner.isPresent()) {
            Set<GamePartner> relacionamentos = gamePartnerService.findAllGamePartnerByPartner(partner.get());
            Set<Game> games = this.gameService.findByGamesDtos(gamesDTO);
            for (GamePartner relacionamento : relacionamentos) {
                if (games.contains(relacionamento.getGame())) {
                    this.gamePartnerService.deleteGamePartner(relacionamento);
                    partner.get().getGames().remove(relacionamento);
                }
            }
            if (partner.get().getGames().isEmpty()) {
                this.partnerRepository.delete(partner.get());
            }
        }else {
            throw new RuntimeException("Partner not found");
        }
    }

    public Page<Partner> findAll(Pageable pageable) {
        return this.partnerRepository.findAll(pageable);
    }

    public Page<Partner> findAllPartners(Pageable pageable) {
        return this.partnerRepository.findAll(pageable);
    }

}
