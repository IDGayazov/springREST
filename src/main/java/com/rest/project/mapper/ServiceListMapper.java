package com.rest.project.mapper;

import com.rest.project.dto.CreateServiceDto;
import com.rest.project.entity.Service;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses=ServiceMapper.class)
public interface ServiceListMapper {

    List<Service> toServiceList(List<CreateServiceDto> createServiceDtos);
    List<CreateServiceDto> toServiceDto(List<Service> services);
}
