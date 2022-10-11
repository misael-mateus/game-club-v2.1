package br.com.projetoweb.controller;

import br.com.projetoweb.model.GameLoan;
import br.com.projetoweb.service.GameLoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("v2/gameloan")
public class LoanController {
    private final GameLoanService gameLoanService;


    public LoanController(GameLoanService gameLoanService) {
        this.gameLoanService = gameLoanService;
    }

    @PostMapping("/{partnerId}")
    public ResponseEntity<Void> loan(@PathVariable Long partnerId, @RequestBody Long mediaId) {
        boolean loan = this.gameLoanService.loan(partnerId, mediaId);
        if (loan)
            return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/return/{partnerId}")
    public ResponseEntity<Void> returnGame(@PathVariable Long partnerId, @RequestBody Long mediaId) {
        boolean loan = this.gameLoanService.returnGame(partnerId, mediaId);
        if (loan)
            return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{partnerId}")
    public ResponseEntity<Set<GameLoan>> findAllLoansByPartnerId(@PathVariable Long partnerId) {
        try {
            return ResponseEntity.ok(this.gameLoanService.findAllByPartnerId(partnerId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
