package br.com.projetoweb.service;

import br.com.projetoweb.dto.GameDTO;
import br.com.projetoweb.dto.PartnerDTO;
import br.com.projetoweb.model.Game;
import br.com.projetoweb.model.Media;
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

    private final MediaService mediaService;

    private final GameService gameService;

    @Autowired
    public PartnerService(PartnerRepository partnerRepository, MediaService mediaService, GameService gameService) {
        this.partnerRepository = partnerRepository;
        this.mediaService = mediaService;
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
            Media MIdia = new Media();
            partnerDB.setGames(MIdia);
            game.setPartners(MIdia);
            MIdia.setPartner(partnerDB);
            MIdia.setGame(game);
            this.mediaService.newGamePartner(MIdia);
        }
        return partnerDB;
    }

    @Transactional
    public Partner deletePartner(Long id) {
        Partner partner = this.partnerRepository.findById(id).orElseThrow(() -> new RuntimeException("Partner not found"));
        this.mediaService.deleteGamePartnerByPartner(partner);
        this.partnerRepository.delete(partner);
        return partner;
    }

    @Transactional
    public void deleteGamesToPartner(Long idPartner,Set<GameDTO> gamesDTO) {
        Optional<Partner> partner = this.partnerRepository.findById(idPartner);
        if (partner.isPresent()) {
            Set<Media> relacionamentos = mediaService.findAllGamePartnerByPartner(partner.get());
            Set<Game> games = this.gameService.findByGamesDtos(gamesDTO);
            for (Media relacionamento : relacionamentos) {
                if (games.contains(relacionamento.getGame())) {
                    this.mediaService.deleteGamePartner(relacionamento);
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

    public Optional<Partner> findById(Long id) {
        return this.partnerRepository.findById(id);
    }

    public Page<Partner> findAll(Pageable pageable) {
        return this.partnerRepository.findAll(pageable);
    }

    public Page<Partner> findAllPartners(Pageable pageable) {
        return this.partnerRepository.findAll(pageable);
    }

}
