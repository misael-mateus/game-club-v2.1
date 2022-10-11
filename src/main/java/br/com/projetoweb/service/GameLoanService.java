package br.com.projetoweb.service;

import br.com.projetoweb.model.GameLoan;
import br.com.projetoweb.model.Media;
import br.com.projetoweb.model.Partner;
import br.com.projetoweb.repository.GameLoanRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static java.time.temporal.ChronoUnit.MINUTES;

@Service
public class GameLoanService {

    private final GameLoanRepository gameLoanRepository;
    private final PartnerService partnerService;
    private final MediaService mediaService;
    private final int MINUTES_TO_RETURN = 1;
    private final int MAX_LOANS = 5;

    public GameLoanService(GameLoanRepository gameLoanRepository, PartnerService partnerService, MediaService mediaService) {
        this.gameLoanRepository = gameLoanRepository;
        this.partnerService = partnerService;
        this.mediaService = mediaService;
    }

    public boolean loan(Long partnerId, Long mediaId) {
        Set<GameLoan> rents = this.gameLoanRepository.findAllByPartnerId(partnerId);
        if (rents.size() >= this.MAX_LOANS)
            return false;

        for (var rent : rents) {
            if (rent.getReturnDate().isAfter(LocalDate.now()) && (!rent.isReturned()))
                return false;
        }

        Optional<Partner> partner = this.partnerService.findById(partnerId);
        Optional<Media> media = this.mediaService.findById(mediaId);
        if (partner.isEmpty() || media.isEmpty())
            return false;

        GameLoan gameLoan = new GameLoan();
        gameLoan.setLoanDate(LocalDate.now());
        gameLoan.setReturnDate(gameLoan.getLoanDate().plus(this.MINUTES_TO_RETURN, MINUTES));
        gameLoan.setPartner(partner.get());
        gameLoan.setMedia(media.get());

        this.gameLoanRepository.save(gameLoan);

        return true;
    }

    public Set<GameLoan> findAllByPartnerId(Long partnerId) {
        return this.gameLoanRepository.findAllByPartnerId(partnerId);
    }

    public boolean returnGame(Long partnerId, Long mediaId) {
        Set<GameLoan> rents = this.gameLoanRepository.findAllByPartnerId(partnerId);
        for (var rent : rents) {
            if (rent.getMedia().getId().equals(mediaId)) {
                rent.setScheduledReturnDate(LocalDate.now());
                rent.setReturned(true);
                this.gameLoanRepository.save(rent);
                return true;
            }
        }
        return false;
    }

}
