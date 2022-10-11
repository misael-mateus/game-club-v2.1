package br.com.projetoweb.service;

import br.com.projetoweb.model.Media;
import br.com.projetoweb.model.Partner;
import br.com.projetoweb.repository.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class MediaService {
    private final MediaRepository mediaRepository;

    @Autowired
    public MediaService(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
    }

    public void newGamePartner(Media MIdia) {
        this.mediaRepository.save(MIdia);
    }

    public void deleteGamePartnerByPartner(Partner partner) {
        this.mediaRepository.deleteGamePartnerByPartner(partner);
    }

    public Optional<Media> findById(Long id) {
        return this.mediaRepository.findById(id);
    }

    public void delete(Media MIdia) {
        this.mediaRepository.delete(MIdia);
    }

    public Set<Media> findAllByGameId(Long gameId) {
        return this.mediaRepository.findAllByGameId(gameId);
    }

    public Set<Media> findAllGamesFindByPartnerId(Long partnerId) {
        return this.mediaRepository.findAllByPartnerId(partnerId);
    }

    public Set<Media> findAllGamePartnerByPartner(Partner partner){
        return this.mediaRepository.findAllByPartner(partner);
    }

    public void deleteGamePartner(Media relacionamento) {
        this.mediaRepository.delete(relacionamento);
    }
}
