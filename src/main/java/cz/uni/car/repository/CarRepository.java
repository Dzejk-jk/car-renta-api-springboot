package cz.uni.car.repository;

import cz.uni.car.entity.CarEntity;
import cz.uni.car.enums.CarStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<CarEntity, Long> {
    List<CarEntity> findByStatus(CarStatus status);
}
