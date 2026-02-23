package cz.uni.car.mapper;

import cz.uni.car.dto.ReservationResponseDTO;
import cz.uni.car.entity.ReservationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CarMapper.class, CustomerMapper.class})
public interface ReservationMapper {
    ReservationResponseDTO toDto(ReservationEntity source);
}
