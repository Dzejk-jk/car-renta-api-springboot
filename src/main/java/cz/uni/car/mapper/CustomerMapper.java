package cz.uni.car.mapper;

import cz.uni.car.dto.CustomerDTO;
import cz.uni.car.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    @Mapping(target = "id", ignore = true)
    CustomerEntity toEntity(CustomerDTO source);
    CustomerDTO toDto(CustomerEntity source);
}
