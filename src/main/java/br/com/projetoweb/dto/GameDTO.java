package br.com.projetoweb.dto;

import br.com.projetoweb.model.Console;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class GameDTO {
    @NotNull(message = "O nome não pode ser nulo")
    @NotBlank(message = "O campo não pode ficar vazio")
    @Size(max = 100)
    private String name;
    @NotNull
    private Console console;
}
