package br.com.projetoweb.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "tb_loan")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class GameLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "partner_id")
    private Partner partner;
    //  @ToString.Exclude
    //  @JsonIgnoreProperties(value = "partners")
    @OneToOne
    @JoinColumn(name = "game_id")
    private Media media;
    private LocalDate loanDate;
    private LocalDate scheduledReturnDate;
    private LocalDate returnDate;
    private boolean returned;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        GameLoan gameLoan = (GameLoan) o;
        return id != null && Objects.equals(id, gameLoan.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
