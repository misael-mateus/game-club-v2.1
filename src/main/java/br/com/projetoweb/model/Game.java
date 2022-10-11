package br.com.projetoweb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name= "tb_game",
        uniqueConstraints = { @UniqueConstraint (name = "game_uq" , columnNames = {
                "name", "console" })})
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false , length = 100)
    private String name;
    @Column(nullable = false , length = 50)
    @Enumerated(EnumType.STRING)
    private Console console;
    @OneToMany(mappedBy = "game")
    @JsonIgnore
    private Set<Media> partners = new HashSet<>();






    public void setPartners(Media... partners) {
        for (Media partner : partners) {
            partner.setGame(this);
            this.partners.add(partner);
        }
    }
}
