package br.com.projetoweb.controller;

import br.com.projetoweb.dto.GameDTO;
import br.com.projetoweb.dto.PartnerDTO;
import br.com.projetoweb.model.Partner;
import br.com.projetoweb.service.PartnerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/v2/partner")
@Api(value = "Partner API", tags = {"Partner"})
@CrossOrigin(origins = "*")
public class PartnerController {
    private final PartnerService partnerService;

    @Autowired
    public PartnerController(PartnerService partnerService) {
        this.partnerService = partnerService;
    }

    @PostMapping
    //@PreAuthorize("hasRole('ADMIN')")
    @CacheEvict(cacheNames = {"lista_de_partners", "lista_de_games"}, allEntries = true)
    public ResponseEntity<Partner> newPartner(@RequestBody PartnerDTO partner) {
        try {
            return ResponseEntity.ok(this.partnerService.newPartner(partner));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping
    @ApiOperation(value = "Busca todos os parceiros")
    @Cacheable("lista_de_partners")
    //@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Page<Partner>> findAllPartners(Pageable page) {
        try {
            return ResponseEntity.ok(this.partnerService.findAllPartners(page));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/games/{idPartner}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePartner(@PathVariable Long idPartner, @RequestBody Set<GameDTO> games) {
        try {
            this.partnerService.deleteGamesToPartner(idPartner, games);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
