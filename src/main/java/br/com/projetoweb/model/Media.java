package br.com.projetoweb.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "tb_media")
@Setter
@Getter
@ToString
@NoArgsConstructor
public class Media {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @ManyToOne
    private Game game;
    @ManyToOne
    private Partner partner;
    @OneToOne(mappedBy = "media")
    private GameLoan gameLoan;
}