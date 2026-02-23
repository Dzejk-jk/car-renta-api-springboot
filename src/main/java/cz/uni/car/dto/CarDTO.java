package cz.uni.car.dto;

import cz.uni.car.enums.CarStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDTO {
    private Long id;

    @NotBlank(message = "Značka nesmí být prázdná")
    private String brand;

    @NotBlank(message = "Model nesmí být prázdný")
    private String model;

    @PastOrPresent(message = "Datum prnví registrace nemůže být v budoucnosti")
    private LocalDate firstRegistration;

    @NotNull(message = "Cena za den nesmí být prázdná")
    @Positive(message = "Cena za den musí být kladné číslo")
    private BigDecimal pricePerDay;

    private CarStatus status;
}
