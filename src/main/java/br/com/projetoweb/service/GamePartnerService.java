package br.com.projetoweb.service;

import br.com.projetoweb.model.GamePartner;
import br.com.projetoweb.model.Partner;
import br.com.projetoweb.repository.GamePartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class GamePartnerService {
    private final GamePartnerRepository gamePartnerRepository;

    @Autowired
    public GamePartnerService(GamePartnerRepository gamePartnerRepository) {
        this.gamePartnerRepository = gamePartnerRepository;
    }

    public void newGamePartner(GamePartner gamePartner) {
        this.gamePartnerRepository.save(gamePartner);
    }

    public void deleteGamePartnerByPartner(Partner partner) {
        this.gamePartnerRepository.deleteGamePartnerByPartner(partner);
    }

    public void delete(GamePartner gamePartner) {
        this.gamePartnerRepository.delete(gamePartner);
    }

    public Set<GamePartner> findAllByGameId(Long gameId) {
        return this.gamePartnerRepository.findAllByGameId(gameId);
    }

    public Set<GamePartner> findAllGamesFindByPartnerId(Long partnerId) {
        return this.gamePartnerRepository.findAllByPartnerId(partnerId);
    }

    public Set<GamePartner> findAllGamePartnerByPartner(Partner partner){
        return this.gamePartnerRepository.findAllByPartner(partner);
    }

    public void deleteGamePartner(GamePartner relacionamento) {
        this.gamePartnerRepository.delete(relacionamento);
    }
}
