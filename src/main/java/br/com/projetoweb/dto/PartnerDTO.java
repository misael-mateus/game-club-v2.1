package br.com.projetoweb.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
public class PartnerDTO {
    @NotNull
    @NotBlank
    @Size(max = 100)
    private String name;
    @NotNull
    @NotBlank
    private String  phoneNumber;
    @NotNull
    private Set<GameDTO> games = new HashSet<>();

}
