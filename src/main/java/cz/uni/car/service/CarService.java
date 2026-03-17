package cz.uni.car.service;

import cz.uni.car.dto.CarDTO;
import cz.uni.car.dto.CarFilterDTO;
import cz.uni.car.entity.CarEntity;
import cz.uni.car.enums.CarStatus;
import cz.uni.car.mapper.CarMapper;
import cz.uni.car.repository.CarRepository;
import cz.uni.car.specification.CarSpecification;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarService {
    private final CarRepository carRepository;
    private final CarMapper carMapper;

    @Autowired
    public CarService(CarRepository carRepository, CarMapper carMapper) {
        this.carRepository = carRepository;
        this.carMapper = carMapper;
    }

    public List<CarDTO> getAll(){
        List<CarEntity> entities = carRepository.findAll();
        List<CarDTO> carDTOs = new ArrayList<>();
        for (CarEntity entity : entities) {
            carDTOs.add(carMapper.toDto(entity));
        }
        return carDTOs;
    }

    public List<CarDTO> getAvailable() {
        List<CarEntity> entities = carRepository.findByStatus(CarStatus.AVAILABLE);
        List<CarDTO> carDTOs = new ArrayList<>();
        for (CarEntity entity : entities) {
            carDTOs.add(carMapper.toDto(entity));
        }
        return carDTOs;
    }

    public CarDTO getById(Long id) {
        CarEntity car = carRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Auto s id " + id + " nenalezeno."));
        return carMapper.toDto(car);
    }

    public CarDTO addCar(CarDTO carDTO) {
        CarEntity car = carMapper.toEntity(carDTO);
        car.setStatus(CarStatus.AVAILABLE);
        return carMapper.toDto(carRepository.save(car));
    }

    public CarDTO updateCar(Long id, CarDTO carDTO) {
        CarEntity car = carRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Auto s id " + id + " nenalezeno."));
        car.setBrand(carDTO.getBrand());
        car.setModel(carDTO.getModel());
        car.setFirstRegistration(carDTO.getFirstRegistration());
        car.setPricePerDay(carDTO.getPricePerDay());
        return carMapper.toDto(carRepository.save(car));
    }

    public CarDTO deleteCar(Long id) {
        CarEntity car = carRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Auto s id " + id + " nenalezeno."));
        CarDTO deletedCar = carMapper.toDto(car);
        carRepository.deleteById(id);
        return deletedCar;
    }

    public Page<CarDTO> search(CarFilterDTO filter) {
        Sort sort = filter.getSortDirection().equalsIgnoreCase("desc")
                ? Sort.by(filter.getSortBy()).descending()
                : Sort.by(filter.getSortBy()).ascending();

        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize(), sort);

        Specification<CarEntity> spec = CarSpecification.filter(
                filter.getBrand(),
                filter.getStatus(),
                filter.getMinPrice(),
                filter.getMaxPrice(),
                filter.getYearFrom(),
                filter.getYearTo()
        );
        return carRepository.findAll(spec, pageable).map(carMapper::toDto);
    }
}
