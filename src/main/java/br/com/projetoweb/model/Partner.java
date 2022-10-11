package br.com.projetoweb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tb_partner",
        uniqueConstraints = {@UniqueConstraint(name = "partner_uq", columnNames = {
                "name", "phoneNumber"})})
public class Partner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String name;
    @Column(nullable = false, length = 15)
    private String phoneNumber;
    @OneToMany(mappedBy = "partner")
    @JsonIgnore
    private Set<Media> games = new HashSet<>();
    @OneToOne(mappedBy = "partner")
    @JsonIgnore
    private GameLoan gameLoan;



    public void setGames(Media... gamers) {
        for (Media gamer : gamers) {
            gamer.setPartner(this);
            this.games.add(gamer);
        }
    }

}
