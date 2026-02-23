package cz.uni.car.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    private Long id;

    @NotBlank(message = "Jméno nesmí být prázdné")
    private String firstName;

    @NotBlank(message = "Příjmení nesmí být prázdné")
    private String lastName;

    @NotBlank(message = "Email nesmí být prázdný")
    @Email(message = "Email nemá správný formát")
    private String email;

    @NotBlank(message = "Telefon nesmí být prázdný")
    @Pattern(regexp = "^[+]?[0-9]{9,13}$", message = "Telefon nemá správný formát. Max 9-13 číslic s nebo bez předvolby.")
    private String phone;
}
