package cz.uni.car.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequestDTO {
    @NotNull(message = "ID zákazníka nesmí být prázdné")
    private Long customerId;

    @NotNull(message = "ID auta nesmí být prázdné")
    private Long carId;

    @NotNull(message = "Datum začátku nesmí být prázdné")
    @FutureOrPresent(message = "Datum začátku nemůže být v minulosti")
    private LocalDate startDate;

    @NotNull(message = "Datum konce nesmí být prázdné")
    @Future(message = "Datum konce musí být v budoucnosti")
    private LocalDate endDate;
}
