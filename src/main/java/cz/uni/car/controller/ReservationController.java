package cz.uni.car.controller;

import cz.uni.car.dto.ReservationRequestDTO;
import cz.uni.car.dto.ReservationResponseDTO;
import cz.uni.car.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ReservationResponseDTO createReservation(@Valid @RequestBody ReservationRequestDTO dto) {
        return reservationService.createReservation(dto);
    }

    @GetMapping("/{id}")
    public ReservationResponseDTO getById(@PathVariable Long id) {
        return reservationService.getById(id);
    }

    @GetMapping("/customer/{customerId}")
    public List<ReservationResponseDTO> getByCustomer(@PathVariable Long customerId) {
        return reservationService.getByCustomer(customerId);
    }

    @PutMapping("/{id}/cancel")
    public ReservationResponseDTO cancelReservation(@PathVariable Long id) {
        return reservationService.cancelReservation(id);
    }

    @PutMapping("/{id}/complete")
    public ReservationResponseDTO completeReservation(@PathVariable Long id) {
        return reservationService.completeReservation(id);
    }
}
