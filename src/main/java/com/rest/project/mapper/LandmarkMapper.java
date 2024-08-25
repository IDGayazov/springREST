package com.rest.project.mapper;

import com.rest.project.dto.CreateLandmarkDto;
import com.rest.project.entity.Landmark;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses={LocalityMapper.class, ServiceListMapper.class})
public interface LandmarkMapper {
    Landmark toEntity(CreateLandmarkDto landmarkDto);
    CreateLandmarkDto toDTO(Landmark landmark);
}
