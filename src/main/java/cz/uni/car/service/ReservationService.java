package cz.uni.car.service;

import cz.uni.car.dto.ReservationRequestDTO;
import cz.uni.car.dto.ReservationResponseDTO;
import cz.uni.car.entity.CarEntity;
import cz.uni.car.entity.CustomerEntity;
import cz.uni.car.entity.ReservationEntity;
import cz.uni.car.enums.CarStatus;
import cz.uni.car.enums.ReservationStatus;
import cz.uni.car.mapper.ReservationMapper;
import cz.uni.car.repository.CarRepository;
import cz.uni.car.repository.CustomerRepository;
import cz.uni.car.repository.ReservationRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private final ReservationMapper reservationMapper;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, CarRepository carRepository,
                              CustomerRepository customerRepository, ReservationMapper reservationMapper) {
        this.reservationRepository = reservationRepository;
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
        this.reservationMapper = reservationMapper;
    }

    @Transactional
    public ReservationResponseDTO createReservation(ReservationRequestDTO request) {
        CustomerEntity customer = customerRepository.findById(request.getCustomerId()).orElseThrow(
                () -> new EntityNotFoundException("Zákazník s id " + request.getCustomerId() + " nenalezen."));
        CarEntity car = carRepository.findById(request.getCarId()).orElseThrow(
                () -> new EntityNotFoundException("Auto s id " + request.getCarId() + " nenalezeno."));

        if (car.getStatus() != CarStatus.AVAILABLE) {
            throw new IllegalStateException("Auto není dostupné k rezervaci.");
        }
        if (!request.getEndDate().isAfter(request.getStartDate())) {
            throw new IllegalArgumentException("Datum konce musí být po datu začátku.");
        }

        long days = ChronoUnit.DAYS.between(request.getStartDate(), request.getEndDate());
        BigDecimal totalPrice = car.getPricePerDay().multiply(BigDecimal.valueOf(days));

        ReservationEntity reservation = new ReservationEntity();
        reservation.setCustomer(customer);
        reservation.setCar(car);
        reservation.setStartDate(request.getStartDate());
        reservation.setEndDate(request.getEndDate());
        reservation.setTotalPrice(totalPrice);
        reservation.setStatus(ReservationStatus.ACTIVE);
        
        car.setStatus(CarStatus.RESERVED);
        carRepository.save(car);
        
        return reservationMapper.toDto(reservationRepository.save(reservation));
    }
    
    @Transactional
    public ReservationResponseDTO cancelReservation(Long id) {
        ReservationEntity reservation = reservationRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Rezervace s id " + id + " nenalezena."));
        
        if (reservation.getStatus() != ReservationStatus.ACTIVE) {
            throw new IllegalStateException("Lze zrušit pouze aktivní rezervaci");
        }
        
        reservation.setStatus(ReservationStatus.CANCELLED);
        reservation.getCar().setStatus(CarStatus.AVAILABLE);
        carRepository.save(reservation.getCar());
        
        return reservationMapper.toDto(reservationRepository.save(reservation));
    }
    
    @Transactional
    public ReservationResponseDTO completeReservation(Long id) {
        ReservationEntity reservation = reservationRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Rezervace s id " + id + " nenalezena."));

        if (reservation.getStatus() != ReservationStatus.ACTIVE) {
            throw new IllegalStateException("Lze dokončit pouze aktivní rezervaci");
        }
        
        reservation.setStatus(ReservationStatus.COMPLETED);
        reservation.getCar().setStatus(CarStatus.AVAILABLE);
        carRepository.save(reservation.getCar());
        
        return reservationMapper.toDto(reservationRepository.save(reservation));
    }
    
    public ReservationResponseDTO getById(Long id) {
        ReservationEntity reservation = reservationRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Rezervace s id " + id + " nenalezena."));
        return reservationMapper.toDto(reservation);
    }

    public List<ReservationResponseDTO> getByCustomer(Long customerId) {
        List<ReservationEntity> entities = reservationRepository.findByCustomerId(customerId);
        List<ReservationResponseDTO> responseDTOS = new ArrayList<>();
        for (ReservationEntity entity : entities) {
            responseDTOS.add(reservationMapper.toDto(entity));
        }
        return responseDTOS;
    }
}
