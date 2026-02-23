package cz.uni.car.controller;

import cz.uni.car.dto.CarDTO;
import cz.uni.car.enums.CarStatus;
import cz.uni.car.service.CarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/cars")
public class CarController {
    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public List<CarDTO> getAll(@RequestParam(required = false) CarStatus status) {
        if (status == null) return carService.getAll();
        if (status == CarStatus.AVAILABLE) return carService.getAvailable();
        return carService.getAll();
    }

    @GetMapping("/{id}")
    public CarDTO getById(@PathVariable Long id) {
        return carService.getById(id);
    }

    @PostMapping
    public CarDTO addCar(@Valid @RequestBody CarDTO carDTO) {
        return carService.addCar(carDTO);
    }

    @PutMapping("/{id}")
    public CarDTO updateCar(@PathVariable Long id, @Valid @RequestBody CarDTO carDTO) {
        return carService.updateCar(id, carDTO);
    }

    @DeleteMapping("/{id}")
    public CarDTO deleteCar(@PathVariable Long id) {
        return carService.deleteCar(id);
    }
}
