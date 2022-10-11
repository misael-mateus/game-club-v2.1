package br.com.projetoweb.repository;

import br.com.projetoweb.model.Media;
import br.com.projetoweb.model.Partner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface MediaRepository extends JpaRepository<Media,Long> {
    Media findByGameIdAndPartnerId(Long gameId, Long partnerId);
    void deleteGamePartnerByPartner(Partner partner);
    Set<Media> findAllByGameId(Long gameId);
    Set<Media> findAllByPartnerId(Long partnerId);
    Set<Media> findAllByPartner(Partner partner);

    void deleteGamePartnerByGameIdAndPartnerId(Long gameId, Long partnerId);
}
