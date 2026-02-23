package cz.uni.car.mapper;

import cz.uni.car.dto.CarDTO;
import cz.uni.car.entity.CarEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CarMapper {
    @Mapping(target = "id", ignore = true)
    CarEntity toEntity(CarDTO source);
    CarDTO toDto(CarEntity source);
}
