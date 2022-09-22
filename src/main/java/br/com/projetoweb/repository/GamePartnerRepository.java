package br.com.projetoweb.repository;

import br.com.projetoweb.model.GamePartner;
import br.com.projetoweb.model.Partner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface GamePartnerRepository extends JpaRepository<GamePartner,Long> {
    GamePartner findByGameIdAndPartnerId(Long gameId, Long partnerId);
    void deleteGamePartnerByPartner(Partner partner);
    Set<GamePartner> findAllByGameId(Long gameId);
    Set<GamePartner> findAllByPartnerId(Long partnerId);
    Set<GamePartner> findAllByPartner(Partner partner);

    void deleteGamePartnerByGameIdAndPartnerId(Long gameId, Long partnerId);
}
