package com.rest.project.mapper;

import com.rest.project.dto.CreateServiceDto;
import com.rest.project.entity.Service;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ServiceMapper {

    Service toEntity(CreateServiceDto serviceDto);

    CreateServiceDto toDTO(Service service);
}
