package cz.uni.car.entity;

import cz.uni.car.enums.CarStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity(name = "car")
@Getter
@Setter
public class CarEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand;
    private String model;
    private LocalDate firstRegistration;
    private BigDecimal pricePerDay;

    @Enumerated(EnumType.STRING)
    private CarStatus status = CarStatus.AVAILABLE;
}
